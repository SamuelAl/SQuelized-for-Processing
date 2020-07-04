package samuelal.squelized;

import java.sql.Types;

import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.CustomSql;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.OrderObject.Dir;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

/* Helper class to support CRUD database operations
 * using SqlQueryBuilder library
 */
public class QueryBuilder {
	
	static DbSchema dbSchema;
	static DbSpec dbSpecs;
	
	
	private static void loadSQLBuilderSchema() {
		dbSpecs = new DbSpec();
		dbSchema = dbSpecs.addDefaultSchema();
	}
	
	public static String createTable(String tableName, String[] columnNames, int[] columnTypes, Integer[] columnLength) {
		
		loadSQLBuilderSchema();
		
		// Specify table name
		DbTable newTable = dbSchema.addTable(tableName);
		
		// Add columns
		for (int i = 0; i < columnNames.length; i++) {
			newTable.addColumn(columnNames[i], columnTypes[i], columnLength[i]);
		}
		
		String query = new CreateTableQuery(newTable, true).validate().toString();
		return query;
	}
	
	public static String insertData(String tableName, String[] columns, Object[] data) {
		
		InsertQuery insertQuery = new InsertQuery(tableName).addCustomColumns(columns, data);
		
		String query = insertQuery.validate().toString();
		
		return query;
		
	}
	
	public static String displayAllTableRecords(String tableName) {
		
		SelectQuery selectQuery = new SelectQuery().addCustomFromTable(tableName).addAllColumns();
		
		String query = selectQuery.validate().toString();
		
		return query;
	}
	
	public static String displayRecords(String tableName, String[] columns) {
		
		SelectQuery selectQuery = new SelectQuery().addCustomFromTable(tableName);
		
		for (String column : columns) {
			selectQuery.addCustomColumns(column);
		}
		
		String query = selectQuery.validate().toString();
		
		return query;	
	}
	
	public static String displayRecords(String tableName, String[] columns, String orderColumn, boolean ascending) {
		
		SelectQuery selectQuery = new SelectQuery().addCustomFromTable(tableName);
		
		for (String column : columns) {
			selectQuery.addCustomColumns(column);
		}
		
		selectQuery.addCustomOrdering(orderColumn, ascending ? Dir.ASCENDING : Dir.DESCENDING);
		
		String query = selectQuery.validate().toString();
		
		return query;
	}
	

}
