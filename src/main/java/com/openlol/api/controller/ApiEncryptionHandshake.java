package com.openlol.api.controller;

import com.openlol.api.model.User;
import com.openlol.api.service.AppAuthService;
import com.openlol.api.service.CryptoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Controller
@RequestMapping(path = "/crypto")
public class ApiEncryptionHandshake {

    AppAuthService appAuthService = new AppAuthService();

    @CrossOrigin
    @GetMapping(path = "/publickey")
    public @ResponseBody
    User getPublicKey(@RequestParam(value = "userId") String userId) throws Exception {
        User user = appAuthService.getUser(userId);

        //server generates RSA key pair - public and private keys
        generateRsaKeyPair(user);

        appAuthService.updateUser(user);

        //to simplify our example, User object is returned with generated RSA public key
        //RSA private key is not included in response because it should be kept as secret
        return user;
    }

    private void generateRsaKeyPair(User user) throws NoSuchAlgorithmException {
        KeyPair keyPair = CryptoUtil.generateRsaKeyPair();

        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();

        //encoding keys to Base64 text format so that we can send public key via REST API
        String rsaPublicKeyBase64 = new String(Base64.getEncoder().encode(publicKey));
        String rsaPrivateKeyBase64 = new String(Base64.getEncoder().encode(privateKey));

        //saving keys to user object for later use
        user.setRsaPublicKey(rsaPublicKeyBase64);
        user.setRsaPrivateKey(rsaPrivateKeyBase64);
    }

}