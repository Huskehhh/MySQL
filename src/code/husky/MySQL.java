package code.husky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Database {
	
	String user = "";
	String database = "";
	String password = "";
	String port = "";
	String hostname = "";
	Connection c = null;

	public MySQL(String hostname, String portnmbr, String database, String username, String password) {
		this.hostname = hostname;
		this.port = portnmbr;
		this.database = database;
		this.user = username;
		this.password = password;
	}
	
	public Connection open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.c = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
			return c;
		} catch (SQLException e) {
			System.out.println("Could not connect to MySQL server! because: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found!");
		}
		return this.c;
	}
	
	public void closeConnection(Connection c) {
		c = null;
	}
}