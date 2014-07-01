package code.husky;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

/**
 * Abstract Database class, serves as a base for any connection method (MySQL,
 * SQLite, etc.)
 * 
 * @author -_Husky_-
 * @author tips48
 */
public abstract class Database {

	/**
	 * Plugin instance, use for plugin.getDataFolder() and plugin.getLogger()
	 */
	protected Plugin plugin;

	/**
	 * Creates a new Database
	 * 
	 * @param plugin
	 *            Plugin instance
	 */
	protected Database(Plugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Opens a connection with the database
	 * 
	 * @throws SQLException
	 *             if the connection can not be opened, ClassNotFoundException
	 *             if the driver cannot be found
	 * @return Connection opened
	 */
	public abstract Connection openConnection() throws SQLException,
			ClassNotFoundException;

	/**
	 * Checks if a connection is open with the database
	 * 
	 * @return true if a connection is open
	 */
	public abstract boolean checkConnection() throws SQLException;

	/**
	 * Gets the connection with the database
	 * 
	 * @return Connection with the database, null if none
	 */
	public abstract Connection getConnection();

	/**
	 * Closes the connection with the database
	 * 
	 * @throws SQLException
	 *             if the connection cannot be closed
	 * @return true if successful
	 */
	public abstract boolean closeConnection() throws SQLException;
}