package pro.husk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

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
     * @return Connection with the database, null if none
     */
    public Connection getConnection() throws SQLException {
        return connection;
    }

    /**
     * Closes the connection with the database
     *
     * @throws SQLException if the connection cannot be closed
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Executes a SQL Query
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return the results of the query, or null if empty
     * @throws SQLException           If the query cannot be executed
     * @throws ClassNotFoundException If the driver cannot be found; see {@link #getConnection()}
     */
    public ResultSet query(String query) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet;
        }

        return null;
    }

    /**
     * Executes an Update SQL Update
     * See {@link java.sql.PreparedStatement#executeUpdate()}
     * If the connection is closed, it will be opened
     *
     * @param update Update to be run
     * @return result code, see {@link java.sql.PreparedStatement#executeUpdate()}
     * @throws SQLException           If the query cannot be executed
     * @throws ClassNotFoundException If the driver cannot be found; see {@link #getConnection()}
     */
    public int update(String update) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(update);

        return statement.executeUpdate();
    }

    /**
     * Executes a SQL Query asynchronously
     *
     * @param query Query to be run
     * @return the results of the query, or null if empty
     * internally throws SQLException           If the query cannot be executed
     * internally throws ClassNotFoundException If the driver cannot be found; see {@link #getConnection()}
     */
    public CompletableFuture<ResultSet> queryAsync(String query) {
        return CompletableFuture.supplyAsync(() -> {
            ResultSet results = null;
            try {
                results = query(query);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return results;
        });
    }

    /**
     * Executes an SQL update asynchronously
     *
     * @param update Update to be run
     * @return result code, see {@link java.sql.PreparedStatement#executeUpdate()}
     * internally throws SQLException           If the query cannot be executed
     * internally throws ClassNotFoundException If the driver cannot be found; see {@link #getConnection()}
     */
    public CompletableFuture<Integer> updateAsync(String update) {
        return CompletableFuture.supplyAsync(() -> {
            int results = 0;
            try {
                results = update(update);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return results;
        });
    }
}