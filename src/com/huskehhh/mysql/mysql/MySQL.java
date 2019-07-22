package com.huskehhh.mysql.mysql;

import com.huskehhh.mysql.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to and uses a MySQL database
 *
 * @author Huskehhh
 * @author tips48
 */
public class MySQL extends Database {
    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;
    private final boolean useSSL;
    private final boolean verifyServerCertificate;

    /**
     * Creates a new MySQL instance
     *
     * @param hostname                Name of the host
     * @param port                    Port number
     * @param username                Username
     * @param password                Password
     * @param useSSL                  SSL Property
     * @param verifyServerCertificate SSL Property
     */
    public MySQL(String hostname, String port, String username,
                 String password, boolean useSSL, boolean verifyServerCertificate) {
        this(hostname, port, null, username, password, useSSL, verifyServerCertificate);
    }

    /**
     * Creates a new MySQL instance for a specific database
     *
     * @param hostname                Name of the host
     * @param port                    Port number
     * @param database                Database name
     * @param username                Username
     * @param password                Password
     * @param useSSL                  SSL Property
     * @param verifyServerCertificate SSL Property
     */
    public MySQL(String hostname, String port, String database,
                 String username, String password, boolean useSSL, boolean verifyServerCertificate) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
        this.useSSL = useSSL;
        this.verifyServerCertificate = verifyServerCertificate;
    }

    @Override
    public Connection openConnection() throws SQLException,
            ClassNotFoundException {
        if (checkConnection()) {
            return connection;
        }

        String connectionURL = "jdbc:mysql://"
                + this.hostname + ":" + this.port;

        if (database != null) {
            connectionURL = connectionURL + "/" + this.database;
        }

        /**
         * Refer: https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-using-ssl.html
         */
        if (verifyServerCertificate) {
            System.setProperty("javax.net.ssl.trustStore", "path_to_truststore_file");
            System.setProperty("javax.net.ssl.trustStorePassword", "mypassword");
        }

        // Append useSSL to end of connectionURL to avoid errors firing off with newer MySQL
        connectionURL = connectionURL + "?useSSL=" + this.useSSL + ";";

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(connectionURL,
                this.user, this.password);

        // Allows implementation of batch statements
        connection.setAutoCommit(false);

        return connection;
    }
}
