package samuelal.squelized;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection extends SQLConnection {

	protected Properties props = new Properties();
	protected String url = "";
	
	/**
	 * Constructor taking in user, password, and url strings
	 * 
	 * 
	 */
	public MySQLConnection(String user, String password, String url) {
		super.welcome();

		this.url = url;
		
		props.setProperty("user", user);
		props.setProperty("password", password);

		//Try connection parameters
		testConnection();
	}

	protected Connection getSQLConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, props);
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
	
	private void testConnection()
	{
		try {
			Connection connection = getSQLConnection();
			System.out.println("Connected as " + props.getProperty("user") + " to " + url);
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		}
	}
}
