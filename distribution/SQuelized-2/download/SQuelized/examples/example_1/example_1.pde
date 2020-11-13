import samuelal.squelized.*;


void setup() {
  
  // Update the url to the chinook sample database for this to work
  // you can download chinook sample database from 
  // https://www.sqlitetutorial.net/sqlite-sample-database/
  // more examples and documentation at https://samuelal.github.io/squelized.github.io/documentation/docs-main.html
  
  SQLConnection myConnection = new SQLiteConnection("jdbc:sqlite:C:/chinook/chinook.db");
  
  Table testTable = myConnection.getTable("employees");
  printTable(testTable);
  
}

void printTable(Table table) {

  for (TableRow row : table.rows())
  {
    for (int i = 0; i < row.getColumnCount(); i++)
    {
      System.out.print(row.getString(i) + "  ");
    }
    System.out.println();
  }
}
