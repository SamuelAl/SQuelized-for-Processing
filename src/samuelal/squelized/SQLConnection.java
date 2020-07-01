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
import java.time.LocalDate;



public abstract class SQLConnection{

	public final static String VERSION = "##library.prettyVersion##";
	protected String url;
	protected String user;
	protected String password;

	protected void welcome() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
	}

	
	/*
	 * runs SQL query through connection
	 * and returns Table object with results
	 * 
	 * @param	String
	 * @return	Table
	 */
	
	protected abstract Connection getSQLConnection();
	
	protected abstract DatabaseType getDatabaseType();
	
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
		}
		catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} 
		return queryResult;
	}

	/**
	 * return the version of the Library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

}

