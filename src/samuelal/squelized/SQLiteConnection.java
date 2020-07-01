package samuelal.squelized;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection extends SQLConnection {


	public SQLiteConnection (String url) {
		super.url = url;
		super.user = "";
		super.password = "";

		//Try connection parameters
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

	@Override
	protected Connection getSQLConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(super.url);
			if (connection.equals(null)) {System.out.println("Null connection");}
		}
		catch (SQLException e) {
			System.out.println("Connection to SQLite database failed");
			System.out.println(e.getMessage());
		}
		return connection;
	}

}
