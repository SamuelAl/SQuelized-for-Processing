package samuelal.squelized;

import processing.core.*;
import processing.data.Table;
import processing.data.TableRow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.time.format.*;
import java.util.Properties;
import java.time.LocalDate;


/**
 * Main class that represents and abstract SQL database
 * connection.
 * Allows for connection functionality to a variety of SQL
 * servers plus basic query creation functionality.
 * 
 * @author Samuel Alarco Cantos
 *
 */
public abstract class SQLConnection{

	public final static String VERSION = "##library.prettyVersion##";


	protected void welcome() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
	}

	
	protected abstract Connection getSQLConnection();
	
	protected abstract DatabaseType getDatabaseType();
	
	
	/**
	 * Runs SQL query through connection
	 * and returns Table object with results.
	 * Cannot be used to run Update Queries.
	 * 
	 * @param query	(String) Query to be executed in database
	 * @return results
	 */
	public Table runQuery(String query)
	{
		Table queryResult = new Table();
		try {
			Connection connection = getSQLConnection();
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);

			//Build results table
			ResultSetMetaData resultsMetaData = results.getMetaData();
			int columnCount = resultsMetaData.getColumnCount();
			for (int i= 1; i <= columnCount; i++) {
				String columnTitle = resultsMetaData.getColumnName(i);          
				queryResult.addColumn(columnTitle);
			}

			// Date format to be used
			// In the future should add functionality to specify data format.
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timestampFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
			
			while (results.next()) {
			
				TableRow newRow = queryResult.addRow();
				for (int i = 0; i < columnCount; i++) {
				
					String columnTitle = resultsMetaData.getColumnName(i + 1);
					int typeNumber = resultsMetaData.getColumnType(i + 1);
					switch(typeNumber)
					{
					// Type String
					case Types.VARCHAR:
					case Types.NVARCHAR:
					case Types.CHAR:
					case Types.NCHAR:
					case Types.LONGVARCHAR:
						newRow.setString(columnTitle, results.getString(columnTitle));
						break;
					// Type Date
					case Types.DATE:
						String parsedDate = "";
						if (getDatabaseType() == DatabaseType.SQLITE) {
							parsedDate = results.getString(columnTitle);
						}
						else {
							parsedDate = dateFormat.format(results.getDate(columnTitle).toLocalDate());
						}
						newRow.setString(columnTitle, parsedDate);
						break;
					// Type Time
					case Types.TIME:
					case Types.TIME_WITH_TIMEZONE:
						String parsedTime = "";
						if (getDatabaseType() == DatabaseType.SQLITE) {
							parsedTime = results.getString(columnTitle);
						}
						else { 
							parsedTime = timestampFormat.format(results.getTime(columnTitle).toLocalTime());
						}
						newRow.setString(columnTitle, parsedTime);
					// Type Time stamps
					case Types.TIMESTAMP:
					case Types.TIMESTAMP_WITH_TIMEZONE:
						String parsedTimeStamp = "";
						if (getDatabaseType() == DatabaseType.SQLITE) {
							parsedTimeStamp = results.getString(columnTitle);
						}
						else {
							parsedTimeStamp = timestampFormat.format(results.getTimestamp(columnTitle).toLocalDateTime());
						}
						newRow.setString(columnTitle, parsedTimeStamp);
						break;
					// Type float
					case Types.FLOAT:
					case Types.REAL:
						newRow.setFloat(columnTitle, results.getFloat(columnTitle));
						break;
					// Type double
					case Types.DOUBLE:
						newRow.setDouble(columnTitle, results.getDouble(columnTitle));
						break;
					// Type int
					case Types.INTEGER:
					case Types.SMALLINT:
					case Types.TINYINT:
						newRow.setInt(columnTitle, results.getInt(columnTitle));
						break;
					// Type long
					case Types.BIGINT:
						newRow.setLong(columnTitle, results.getLong(columnTitle));
						break;
					default:
						newRow.setString(columnTitle, results.getString(columnTitle));
						break;
					}
				}
			}
			statement.close();
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} 
		return queryResult;
	}

	/**
	 * Runs an Update SQL query through connection
	 * 
	 * @param query	(String) Update Query to be executed in database
	 */
	public void updateQuery(String query) {
		try {
			Connection connection = getSQLConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} 
	}
	
	/**
	 * Gets all the data of a table and
	 * generates a Processing Table with it
	 * 
	 * @param tableName (String) Name of the table to display
	 * @return results
	 */
	public Table getTable(String tableName) {
		return runQuery(QueryBuilder.displayAllTableRecords(tableName));
	}
	
	/**
	 * Gets all the data in a specific table column
	 * and generates Processing Table 
	 * 
	 * @param tableName (String) Name of the table to display
	 * @param columnName (String) Name of column to extract data from
	 * @return results
	 */
	public Table getColumn(String tableName, String columnName) {
		return getColumns(tableName, new String[] {columnName});
	}
	
	/**
	 * Gets all the date of a table's specified columns
	 * and generates Processing Table
	 * 
	 * @param tableName (String) Name of the table to display
	 * @param columnNames (String[]) Array containing names of the columns to extract data from
	 * @return results
	 */
	public Table getColumns(String tableName, String[] columnNames) {
		return runQuery(QueryBuilder.displayRecords(tableName, columnNames));
	}
	
	/**
	 * Inserts values into specified columns
	 * (inserts only one row)
	 * 
	 * @param tableName (String) Name of the table to insert into
	 * @param columnNames (String[]) Array containing the columns into which insert data (same order as corresponding element)
	 * @param data (Object[]) Array containing data objects to insert into respective columns (same order as columns)
	 */
	public void insertIntoColumns(String tableName, String[] columnNames, Object[] data) {
		updateQuery(QueryBuilder.insertData(tableName, columnNames, data));
	}
	
	/**
	 * Return the version of the Library.
	 * 
	 * @return VERSION
	 */
	public static String version() {
		return VERSION;
	}

}

