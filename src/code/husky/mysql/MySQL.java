package code.husky.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.plugin.Plugin;

import code.husky.Database;

/**
 * Connects to and uses a MySQL database
 * 
 * @author -_Husky_-
 * @author tips48
 */
public class MySQL extends Database {
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;

	private Connection connection;

	/**
	 * Creates a new MySQL instance
	 * 
	 * @param plugin
	 *            Plugin instance
	 * @param hostname
	 *            Name of the host
	 * @param port
	 *            Port number
	 * @param database
	 *            Database name
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 */
	public MySQL(Plugin plugin, String hostname, String port, String database,
			String username, String password) {
		super(plugin);
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = null;
	}

	@Override
	public Connection openConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://"
				+ this.hostname + ":" + this.port + "/" + this.database,
				this.user, this.password);
		return connection;
	}

	@Override
	public boolean checkConnection() throws SQLException {
		return connection != null && !connection.isClosed();
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public boolean closeConnection() throws SQLException {
		if (connection == null) {
			return false;
		}
		connection.close();
		return true;
	}

	public ResultSet querySQL(String query) {
		Connection c = null;

		try {
			if (checkConnection()) {
				c = getConnection();
			} else {
				c = openConnection();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Statement s = null;

		try {
			s = c.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		ResultSet ret = null;

		try {
			ret = s.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public void updateSQL(String update) {

		Connection c = null;

		try {
			if (checkConnection()) {
				c = getConnection();
			} else {
				c = openConnection();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Statement s = null;

		try {
			s = c.createStatement();
			s.executeUpdate(update);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
