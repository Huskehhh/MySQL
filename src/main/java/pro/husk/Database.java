package pro.husk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Abstract Database class, serves as a base for any connection method (MySQL,
 * SQLite, etc.)
 *
 * @author Huskehhh
 * @author tips48
 * @author Ktar5
 */
public abstract class Database {

    protected Connection connection;

    /**
     * Creates a new Database
     */
    protected Database() {
        this.connection = null;
    }

    /**
     * Opens a connection with the database
     *
     * @return Opened connection
     * @throws SQLException           if the connection can not be opened
     * @throws ClassNotFoundException if the driver cannot be found
     */
    public abstract Connection openConnection() throws SQLException,
            ClassNotFoundException;

    /**
     * Checks if a connection is open with the database
     *
     * @return true if the connection is open
     * @throws SQLException if the connection cannot be checked
     */
    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    /**
     * Gets the connection with the database
     *
     * @return Connection with the database, null if none
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the connection with the database
     *
     * @return true if successful
     * @throws SQLException if the connection cannot be closed
     */
    public boolean closeConnection() throws SQLException {
        if (connection == null) {
            return false;
        }
        connection.close();
        return true;
    }

    /**
     * Executes a SQL Query
     * <p>
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return the results of the query
     * @throws SQLException           If the query cannot be executed
     * @throws ClassNotFoundException If the driver cannot be found; see {@link #openConnection()}
     */
    public ResultSet querySQL(String query) throws SQLException,
            ClassNotFoundException {
        if (!checkConnection()) {
            openConnection();
        }

        Statement statement = connection.createStatement();

        ResultSet ResultSet = statement.executeQuery(query);

        connection.commit();

        return ResultSet;
    }

    /**
     * Executes an Update SQL Query
     * See {@link java.sql.Statement#executeUpdate(String)}
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return Result Code, see {@link java.sql.Statement#executeUpdate(String)}
     * @throws SQLException           If the query cannot be executed
     * @throws ClassNotFoundException If the driver cannot be found; see {@link #openConnection()}
     */
    public int updateSQL(String query) throws SQLException,
            ClassNotFoundException {
        if (!checkConnection()) {
            openConnection();
        }

        Statement statement = connection.createStatement();

        int ResultCode = statement.executeUpdate(query);

        connection.commit();

        return ResultCode;
    }

    /**
     * Executes a Batch SQL Query
     * Batch SQL Queries are a more efficient way of
     * sending multiple SQL statements at a time.
     * See {@link java.sql.Statement#executeBatch}
     * If the connection is closed, it will be opened
     *
     * @param stmts The statements to be executed, stored in an array
     * @return Result Code, see {@link java.sql.Statement#executeBatch()}
     * @throws SQLException           If the query cannot be executed
     * @throws ClassNotFoundException If the driver cannot be found; see {@link #openConnection()}
     */
    public int[] sendBatchStatement(String[] stmts) throws SQLException,
            ClassNotFoundException {
        if (!checkConnection()) {
            openConnection();
        }

        Statement statement = connection.createStatement();

        for (String state : stmts) {
            statement.addBatch(state);
        }

        int[] ResultCodes = statement.executeBatch();

        connection.commit();

        return ResultCodes;
    }
}