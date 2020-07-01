package samuelal.squelized;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection extends SQLConnection {

	
	/**
	 * a Constructor, usually called in the setup() 
	 * 
	 * 
	 */
	public MySQLConnection(String user, String password, String url) {
		super.welcome();
		super.user = user;
		super.password = password;
		super.url = url;

		//Try connection parameters
		try {
			Connection connection = getSQLConnection();
			System.out.println("Connected as " + user + " to " + url);
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		}
	}

	protected Connection getSQLConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(super.url, super.user, super.password);
			if (connection.equals(null)) {System.out.println("Null connection");}
		}
		catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		}
		return connection;
	}

	@Override
	protected DatabaseType getDatabaseType() {
		return DatabaseType.MYSQL;
	}
}
