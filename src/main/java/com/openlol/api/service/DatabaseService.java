package com.openlol.api.service;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import org.springframework.stereotype.Service;


@Service
public class DatabaseService {
    private static RethinkDB dbConnection;

    public void getConnection() {
        final RethinkDB r = RethinkDB.r;
        Connection conn = r.connection().hostname("localhost").port(28015).connect();

    }
}
