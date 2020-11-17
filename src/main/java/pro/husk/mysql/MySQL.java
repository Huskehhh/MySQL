package pro.husk.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import pro.husk.Database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connects to and uses a MySQL database through Hikari connection pool
 *
 * @author Huskehhh
 * @author tips48
 */
public class MySQL extends Database {

    private final HikariDataSource dataSource;

    /**
     * Creates a new MySQL instance
     *
     * @param url      | URL of the database
     * @param username | Username
     * @param password | Password
     */
    public MySQL(String url, String username, String password) {
        this(url, username, password, false);
    }

    /**
     * Creates a new MySQL instance
     *
     * @param url          | URL of the database
     * @param username     | Username
     * @param password     | Password
     * @param legacyDriver | Whether or not using a legacy driver, used to fix "Failed to get driver instance"
     */
    public MySQL(String url, String username, String password, boolean legacyDriver) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        if (legacyDriver) config.setDataSourceClassName("com.mysql.jdbc.Driver");

        // See: https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");

        dataSource = new HikariDataSource(config);
    }

    /**
     * Getter for the {@link HikariDataSource} object
     *
     * @return {@link HikariDataSource} object
     */
    public HikariDataSource getDataSource() {
        return dataSource;
    }

    /**
     * Gets current connection, or a new connection from {@link HikariDataSource}
     *
     * @return connection
     * @throws SQLException if cannot get a connection
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
        return connection;
    }
}
