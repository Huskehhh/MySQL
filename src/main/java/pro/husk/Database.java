package pro.husk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
     * <p>
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return the results of the query, or null if empty
     * @throws SQLException           If the query cannot be executed
     * @throws ClassNotFoundException If the driver cannot be found; see {@link #getConnection()} ()}
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
     * Executes an Update SQL Query
     * See {@link java.sql.PreparedStatement#executeUpdate()}
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return result code, see {@link java.sql.PreparedStatement#executeUpdate()}
     * @throws SQLException           If the query cannot be executed
     * @throws ClassNotFoundException If the driver cannot be found; see {@link #getConnection()} ()}
     */
    public int update(String query) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(query);

        return statement.executeUpdate();
    }
}