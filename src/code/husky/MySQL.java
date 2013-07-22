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
			c = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
		} catch (SQLException e) {
			System.out.println("Could not connect to MySQL server! because: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found!");
		}
		return c;
	}
	
	public void closeConnection(Connection c) {
		c = null;
	}
    
    public void createTable(String tablename) {
        try {
            Statement s = open().createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS '" + tablename + "';";
                            } catch SQLException sqle{
                                System.out.println("Error : " + sqle.getMessage());
                            }
                            }
                            
                            public boolean contentExists(String query) {
                try {
                    Statement s = open().createStatement();
                    Resultset res = s.executeQuery(query);
                    if(res != null) {
                        return true;
                    } else {
                        return false;
                    }
                } catch SQLException sqle {
                    System.out.println("Error : " + sqle.getMessage());
                }
            }
                            
}