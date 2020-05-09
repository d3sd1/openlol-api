package com.openlol.api.config;

import com.openlol.api.service.CryptoUtil;
import com.rethinkdb.net.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;

@Component
public class WebSocketAuthenticatorService {
    @Autowired
    private DatabaseConfig databaseConfig;

    // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String pcUuid) throws AuthenticationException, NoSuchAlgorithmException {


        // search on db
        Result<Object> foundOpenlolUser = this.databaseConfig.getR().
                table("openlol_user").filter(row -> row.g("pc_uuid").eq(pcUuid))
                .run(this.databaseConfig.getConnection());

        // if no results, add it
        if (foundOpenlolUser.bufferedCount() == 0) {
            this.databaseConfig.getR().
                    table("openlol_user").insert(
                    this.databaseConfig.getR().array(
                            this.databaseConfig.getR().hashMap("pc_uuid", pcUuid)
                    )
            ).run(this.databaseConfig.getConnection());
        }


        //server generates RSA key pair - public and private keys

        KeyPair keyPair = CryptoUtil.generateRsaKeyPair();

        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();

        //encoding keys to Base64 text format so that we can send public key via REST API
        String rsaPublicKeyBase64 = new String(Base64.getEncoder().encode(publicKey));
        String rsaPrivateKeyBase64 = new String(Base64.getEncoder().encode(privateKey));

        this.databaseConfig.getR().table("openlol_user").filter(
                row -> row.g("pc_uuid").eq(pcUuid)
        )
                .update(this.databaseConfig.getR().array(
                        this.databaseConfig.getR().hashMap("rsa_public_key", rsaPublicKeyBase64)
                )).run(this.databaseConfig.getConnection());
        this.databaseConfig.getR().table("openlol_user").filter(
                row -> row.g("pc_uuid").eq(pcUuid)
        )
                .update(this.databaseConfig.getR().array(
                        this.databaseConfig.getR().hashMap("rsa_private_key", rsaPrivateKeyBase64)
                )).run(this.databaseConfig.getConnection());

        //to simplify our example, User object is returned with generated RSA public key
        //RSA private key is not included in response because it should be kept as secret
        // null credentials, we do not pass the password along
        return new UsernamePasswordAuthenticationToken(
                pcUuid,
                null,
                Collections.singleton((GrantedAuthority) () -> "USER") // MUST provide at least one role
        );
    }
}