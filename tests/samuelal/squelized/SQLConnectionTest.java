package samuelal.squelized;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SQLConnectionTest {
	
	Properties mySqlProps;
	Properties postGreSQLProps;
	public SQLConnectionTest() {
		mySqlProps = new Properties();
		mySqlProps.setProperty("user", "root");
		mySqlProps.setProperty("password", "1234");
		mySqlProps.setProperty("useUnicode", "true");
		mySqlProps.setProperty("useJDBCCompliantTimezoneShift", "true");
		mySqlProps.setProperty("useLegacyDatetimecode", "true");
		mySqlProps.setProperty("serverTimezone", "UTC");
	}

	@Test
	void testGetSQLConnection() {
		SQLConnection mysql = new MySQLConnection("jdbc:mysql://localhost:3306/squelized_test", mySqlProps);
	}

	@Test
	void testGetDatabaseType() {
		SQLConnection mySQL = new MySQLConnection("jdbc:mysql://localhost:3306/squelized_test", mySqlProps);
		Assert.assertEquals(DatabaseType.MYSQL, mySQL.getDatabaseType());
		SQLConnection sqlLite = new SQLiteConnection("jdbc:sqlite:E:/Personal Projects/SQuelized/chinook/chinook.db");
		Assert.assertEquals(DatabaseType.SQLITE, sqlLite.getDatabaseType());
	}

	@Test
	void testRunQuery() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetTable() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetColumn() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetColumns() {
		fail("Not yet implemented"); // TODO
	}

}
