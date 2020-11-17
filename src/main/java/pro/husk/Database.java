package pro.husk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Abstract Database class, serves as a base for any connection method (MySQL,
 * SQLite, etc.)
 *
 * @author Huskehhh
 * @author tips48
 * @author Ktar5
 */
public abstract class Database {

    // Connection to the database
    protected Connection connection;

    /**
     * Creates a new Database
     */
    protected Database() {
        this.connection = null;
    }

    /**
     * Checks if a connection is open with the database
     *
     * @return true if the connection is open
     * @throws SQLException if the connection cannot be checked
     */
    private boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    /**
     * Gets the connection with the database
     *
     * @return Connection with the database, will initialise new connection if dead
     * @throws SQLException if cannot get a connection
     */
    public abstract Connection getConnection() throws SQLException;

    /**
     * Closes the connection with the database
     *
     * @throws SQLException if the connection cannot be closed
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Executes a SQL Query and returns a ResultSet
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return {@link ResultSet}
     * @throws SQLException If the query cannot be executed
     */
    public ResultSet query(String query) throws SQLException {
        if (!checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(query);

        return statement.executeQuery();
    }

    /**
     * Executes a SQL Query
     * If the connection is closed, it will be opened
     *
     * @param query    Query to be run
     * @param consumer to pass {@link ResultSet} to
     * @throws SQLException If the query cannot be executed
     */
    public void query(String query, SQLConsumer<ResultSet> consumer) throws SQLException {
        ResultSet resultSet = query(query);

        consumer.accept(resultSet);

        resultSet.close();
        resultSet.getStatement().close();
    }

    /**
     * Executes a SQL Query and returns a {@link CompletableFuture} of a {@link ResultSet}
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return {@link CompletableFuture} containing {@link ResultSet} object
     * internally throws {@link SQLException} If the query cannot be executed
     */
    public CompletableFuture<ResultSet> queryAsync(String query) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return query(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * Executes an Update SQL Update
     * See {@link java.sql.PreparedStatement#executeUpdate()}
     * If the connection is closed, it will be opened
     *
     * @param update Update to be run
     * @return result code, see {@link java.sql.PreparedStatement#executeUpdate()}
     * @throws SQLException If the query cannot be executed
     */
    public int update(String update) throws SQLException {
        if (!checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(update);
        int result = statement.executeUpdate();

        statement.close();

        return result;
    }

    /**
     * Executes an SQL update asynchronously
     *
     * @param update Update to be run
     * @return {@link CompletableFuture} containing result code, see {@link java.sql.PreparedStatement#executeUpdate()}
     * internally throws {@link SQLException}           If the query cannot be executed
     * internally throws {@link ClassNotFoundException} If the driver cannot be found; see {@link #getConnection()}
     */
    public CompletableFuture<Integer> updateAsync(String update) {
        return CompletableFuture.supplyAsync(() -> {
            int results = 0;
            try {
                results = update(update);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return results;
        });
    }
}