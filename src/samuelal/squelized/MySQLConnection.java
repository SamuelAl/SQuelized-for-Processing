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


/**
 * This is a template class and can be used to start a new processing Library.
 * Make sure you rename this class as well as the name of the example package 'template' 
 * to your own Library naming convention.
 * 
 * (the tag example followed by the name of an example included in folder 'examples' will
 * automatically include the example in the javadoc.)
 *
 * @example Hello 
 */

public class MySQLConnection{
	
	public final static String VERSION = "##library.prettyVersion##";
	private String url, user, password;
	private Connection connection;

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the Library.
	 * 
	 */
	public MySQLConnection(String user, String password, String url) {
		welcome();
		this.user = user;
		this.password = password;
		this.url = url;
		
		//Try connection parameters
		try {
			connection = DriverManager.getConnection(url, user, password);
			connection.close();
		}
		catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		}
	}
	
	
	private void welcome() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
	}
	
	public Table runQuery(String query)
	{
		Table queryResult = new Table();
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
	        Statement statement = connection.createStatement();
	        ResultSet results = statement.executeQuery(query);
	        
	        //Build results table
	        ResultSetMetaData resultsMetaData = results.getMetaData();
	        int columnCount = resultsMetaData.getColumnCount();
	        for (int i= 1; i <= columnCount; i++)
	        {
	          String columnTitle = resultsMetaData.getColumnName(i);          
	          queryResult.addColumn(columnTitle);
	        }
	        
	        // Date format to be used
	        // In the future should add functionality to specify data format.
	        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        DateTimeFormatter  timestampFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	        while (results.next())
	        {
	          TableRow newRow = queryResult.addRow();
	          for (int i = 0; i < columnCount; i++)
	          {
	            String columnTitle = resultsMetaData.getColumnName(i + 1);
	            int typeNumber = resultsMetaData.getColumnType(i + 1);
	            switch(typeNumber)
	            {
	              // Type String
	            case Types.VARCHAR:
	            case Types.CHAR:
	            case Types.LONGVARCHAR:
	              newRow.setString(columnTitle, results.getString(columnTitle));
	              break;
	              // Type Date
	            case Types.DATE:
	              String parsedDate = dateFormat.format(results.getDate(columnTitle).toLocalDate());
	              newRow.setString(columnTitle, parsedDate);
	              break;
	              // Type Timestamps
	            case Types.TIME:
	            	String parsedTime = timestampFormat.format(results.getTime(columnTitle).toLocalTime());
	            	newRow.setString(columnTitle, parsedTime);
	            case Types.TIMESTAMP:
	            	String parsedTimeStamp = timestampFormat.format(results.getTimestamp(columnTitle).toLocalDateTime());
	            	newRow.setString(columnTitle, parsedTimeStamp);
	              // Type float
	            case Types.FLOAT:
	            case Types.DOUBLE:
	              newRow.setFloat(columnTitle, results.getFloat(columnTitle));
	              break;
	              // Type int
	            case Types.INTEGER:
	              newRow.setInt(columnTitle, results.getInt(columnTitle));
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

