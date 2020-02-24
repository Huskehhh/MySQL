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

    // Hikari datasource
    private final HikariDataSource dataSource;

    /**
     * Creates a new MySQL instance for a specific database
     *
     * @param hostname | Name of the host
     * @param port     | Port number
     * @param database | Database name
     * @param username | Username
     * @param password | Password
     * @param params   | Extra parameters
     */
    public MySQL(String hostname, String port, String database, String username, String password, String params) {

        // Build URL of database
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append("jdbc:mysql://");
        urlBuild.append(hostname);
        urlBuild.append(":");
        urlBuild.append(port);
        urlBuild.append("/");
        urlBuild.append(database);

        // Params to db url
        if (!params.isEmpty()) {

            if (!params.startsWith("?")) urlBuild.append("?");

            urlBuild.append(params);
        }

        // Begin configuration of Hikari DataSource
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(urlBuild.toString());
        config.setUsername(username);
        config.setPassword(password);

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
     * Getter for the Hikari DataSource object
     *
     * @return Hikari DataSource object
     */
    public HikariDataSource getDataSource() {
        return dataSource;
    }

    /**
     * Gets connection from Hikari DataSource
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
