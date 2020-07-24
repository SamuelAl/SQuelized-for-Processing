package samuelal.squelized;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Represents connection to a MySQL database.
 * Extends SQLConnection.
 * 
 * @author Samuel Alarco Cantos
 *
 */
public class MySQLConnection extends SQLConnection {

	protected Properties props;
	protected String url = "";
	
	/**
	 * Constructor taking in user, password, and url strings
	 * 
	 * @param user
	 * @param assword
	 * @param url
	 */
	public MySQLConnection(String user, String password, String url) {
		
		super.welcome();
		this.url = url;
		
		props = new Properties();
		
		props.setProperty("user", user);
		props.setProperty("password", password);

		//Try connection parameters
		testConnection();
	}
	
	/**
	 * Constructor with full url
	 * 
	 * @param url
	 */
	public MySQLConnection(String url) {
		super.welcome();
		this.url = url;
		props = new Properties();
		
		//Try connection parameters
		testConnection();
	}
	
	/**
	 * Constructor with url and pre-made Properties object
	 * 
	 * @param url
	 * @param Properties props
	 */
	public MySQLConnection(String url, Properties props) {
		super.welcome();
		this.url = url;
		this.props = props;
		
		//Try connection parameters
		testConnection();
	}
	
	/**
	 * Factory method to create connection object
	 * 
	 * @return Connection connection object
	 */
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
	
	/**
	 * Tests SQL connection to database 
	 * using provided settings
	 */
	private void testConnection()
	{
		try {
			Connection connection = getSQLConnection();
			System.out.println("Connected as to " + url);
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		}
	}
}
