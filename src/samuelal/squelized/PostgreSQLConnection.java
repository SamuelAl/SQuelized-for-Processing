package samuelal.squelized;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLConnection extends SQLConnection {

	protected Properties props;
	protected String url = "";
	
	/**
	 * Constructor with full url
	 * 
	 * @param String url
	 */
	public PostgreSQLConnection(String url) {
		super.welcome();
		this.url = url;
		
		props = new Properties();

		//Try connection parameters
		testConnection();
	}
	
	/**
	 * Constructor with url and pre-made Properties object
	 * 
	 * @param String url
	 * @param Properties props
	 */
	public PostgreSQLConnection(String url, Properties props) {
		super.welcome();
		this.url = url;
		this.props = props;
		

		//Try connection parameters
		testConnection();
	}
	
	/**
	 * Constructor taking in user, password, and url strings
	 * 
	 * @param String user
	 * @param String password
	 * @param String url
	 */
	public PostgreSQLConnection(String user, String password, String url) {
		
		super.welcome();
		this.url = url;
		
		props = new Properties();
		
		props.setProperty("user", user);
		props.setProperty("password", password);

		//Try connection parameters
		testConnection();
	}
	
	/**
	 * Factory method to create connection object
	 * 
	 * @return Connection connection object
	 */
	@Override
	protected Connection getSQLConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, props);
			if (connection.equals(null)) {System.out.println("Null connection");}
		}
		catch (SQLException e) {
			System.out.println("Connection to PostgreSQL database failed");
			System.out.println(e.getMessage());
		}
		return connection;
	}

	@Override
	protected DatabaseType getDatabaseType() {
		return DatabaseType.POSTGRESQL;
	}
	
	private void testConnection()
	{
		try {
			Connection connection = getSQLConnection();
			System.out.println("Connected to " + url);
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Connection to PostgreSQL database failed");
			System.out.println(e.getMessage());
		}
	}

}
