package samuelal.squelized;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection extends SQLConnection {

	protected String url = "";

	/**
	 * Constructor with full url
	 * 
	 * @param String url
	 */
	public SQLiteConnection (String url) {

		this.url = url;

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
			connection = DriverManager.getConnection(url);
			if (connection.equals(null)) {System.out.println("Null connection");}
		}
		catch (SQLException e) {
			System.out.println("Connection to SQLite database failed");
			System.out.println(e.getMessage());
		}
		return connection;
	}

	@Override
	protected DatabaseType getDatabaseType() {
		return DatabaseType.SQLITE;
	}
	
	/**
	 * Tests SQL connection to database 
	 * using provided settings
	 */
	// could be refactored to avoid repetition
	private void testConnection()
	{
		try {
			Connection connection = getSQLConnection();
			System.out.println("Connected to " + url);
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Connection to SQLite database failed");
			System.out.println(e.getMessage());
		}
	}

}
