package org.openjfx.demo.database;

import org.openjfx.demo.HelloApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final String URL = Objects.requireNonNull(HelloApplication.class.getResource("/database.db")).getPath();

    public static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + URL);
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            URL);
            return null;
        }
        return connection;
    }
}
