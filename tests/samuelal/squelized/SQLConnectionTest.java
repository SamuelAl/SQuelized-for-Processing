package samuelal.squelized;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import org.junit.jupiter.api.Test;

class SQLConnectionTest {

	@Test
	void testGetSQLConnection() {
		Properties mySqlProps = new Properties();
		mySqlProps.setProperty("user", "root");
		mySqlProps.setProperty("password", "1234");
		mySqlProps.setProperty("useUnicode", "true");
		mySqlProps.setProperty("useJDBCCompliantTimezoneShift", "true");
		mySqlProps.setProperty("useLegacyDatetimecode", "true");
		mySqlProps.setProperty("serverTimezone", "UTC");
		SQLConnection mysql = new MySQLConnection("jdbc:mysql://localhost:3306/squelized_test", mySqlProps);
	}

	@Test
	void testGetDatabaseType() {
		fail("Not yet implemented"); // TODO
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
