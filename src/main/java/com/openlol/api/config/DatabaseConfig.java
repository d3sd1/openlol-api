package com.openlol.api.config;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DatabaseConfig {
    private final RethinkDB r = RethinkDB.r;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private Connection dbConnection;

    @Bean
    public void configureDb() {
        this.setupPool();
        this.setupEnvDb();
    }

    private void setupEnvDb() {
        try {
            r.dbCreate(this.activeProfile).run(this.dbConnection);
        } catch (ReqlOpFailedError e) {
            // Db already exists, ignoring this.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupPool() {
        this.dbConnection = this.r.connection().hostname("207.180.211.39").port(28015).connect();
    }

    public Connection getConnection() {
        return this.dbConnection;
    }

    public RethinkDB getR() {
        return this.r;
    }
}
