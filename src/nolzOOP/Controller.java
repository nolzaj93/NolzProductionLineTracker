package nolzOOP;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

  @FXML
  private ComboBox quantity;

  @FXML
  private ComboBox itemTypeCombo;

  @FXML
  private TextField productNameField;

  @FXML
  private TextField manufacturerField;

  private static Statement stmt;
  static final String JDBC_DRIVER = "org.h2.Driver";

  static final String DB_URL = "jdbc:h2:./res/HR;DB_CLOSE_DELAY=-1";
  //  Database credentials

  static final String USER = "nolzaj93";
  static final String PASS = "a050593n";

  private TextArea ta = new TextArea();
  private Button btShowJobs = new Button("Show Records");

  public void initialize() {

    // Populate Item Type ComboBox
    itemTypeCombo.getItems().add("AUDIO");
    itemTypeCombo.getItems().add("VIDEO");
    itemTypeCombo.getItems().add("AUDIOMOBILE");
    itemTypeCombo.getItems().add("VIDEOMOBILE");

    // Populates the comboBox named quantity with numbers 1-10
    for (int count = 1; count <= 10; count++) {
      quantity.getItems().add(count);
    }

    //Allows the user to add an entry
    quantity.setEditable(true);

    // Show 1 as the default value.
    quantity.getSelectionModel().selectFirst();
  }

  protected static void initializeDB() {
    // intelliJ must be disconnected from the database in order for program to connect
    // Hit the red square to stop intelliJ connection to database before running the program

    // Right click on database, Diagrams, Open Visualization
    // Need a database diagram for the Production Line Project

    Connection conn = null;

    //Exact same for every database
    //Result set looks like the database table
    try {

      // STEP 1: Register JDBC driver

      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query

      stmt = conn.createStatement();

//      String sql = "SHOW TABLES";
//
//      ResultSet rs = stmt.executeQuery(sql);

//      DatabaseMetaData dbmd = conn.getMetaData();
//      ResultSet rsTables = dbmd.getTables(null, null, "JOB%",
//          new String[]{"TABLE"});
//
//      while (rsTables.next()) {
//       // cboTableName.getItems().add(rsTables.getString("TABLE_NAME"));
//      }

    } catch (ClassNotFoundException e) {

      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  protected void addNewProduct(ActionEvent event) {
    String itemType = (String) itemTypeCombo.getValue();
    // Show 1 as the default value.
    itemTypeCombo.getSelectionModel().selectFirst();

    String productName = productNameField.getText();
    productNameField.clear();
    String manufacturer = manufacturerField.getText();
    manufacturerField.clear();

    try {
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      Connection conn = null;

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");

      //STEP 4: Execute a query
      System.out.println("Inserting records into the table...");

      //INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );
      //Create update statement that will select from the chosen table name
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      String sql = "INSERT INTO PRODUCT VALUES (" + productName
          + ", " + itemType + ", " + manufacturer + ")";
      stmt.executeUpdate(sql);
//
//      ResultSetMetaData rsmd = rs.getMetaData();
//
//      int numberOfColumns = rsmd.getColumnCount();
//
//      // Prints column names in text area
//      for (int i = 1; i <= numberOfColumns; i++) {
//        ta.appendText(rsmd.getColumnName(i) + " ");
//      }
//      ta.appendText("\n");
//
//      while (rs.next()) {
//
//        // Prints data for each column
//        for (int i = 1; i <= numberOfColumns; i++) {
//          ta.appendText(rs.getString(i) + " ");
//        }
//        ta.appendText("\n");
//      }

      // STEP 4: Clean-up environment

    } catch (SQLException e) {

      e.printStackTrace();

    } catch (Exception e) {

      e.printStackTrace();

    }
    //Create a ResultSet object to hold the data from the executed query

    //Use the MetaData from the ResultSet to append the column names to the text area

    //Use a while loop to display the values of the returned data to the test area
  }
//  private void showData() {
//    String itemType = itemTypeCombo.getValue();
//    itemTypeCombo.clear();
//
//    String productName = productNameField.getValue();
//    productNameField.clear();
//    String manufacturer = manufacturerField.getValue();
//    manufacturerField.clear();
//    try {
//      //Create query that will select from the chosen table name
//
//      String sql = "SELECT * FROM " + tableName;
//
//      ResultSet rs = stmt.executeQuery(sql);
//
//      ResultSetMetaData rsmd = rs.getMetaData();
//
//      int numberOfColumns = rsmd.getColumnCount();
//
//      // Prints column names in text area
//      for (int i = 1; i <= numberOfColumns; i++) {
//        ta.appendText(rsmd.getColumnName(i) + " ");
//      }
//      ta.appendText("\n");
//
//      while (rs.next()) {
//
//        // Prints data for each column
//        for (int i = 1; i <= numberOfColumns; i++) {
//          ta.appendText(rs.getString(i) + " ");
//        }
//        ta.appendText("\n");
//      }
//
//      // STEP 4: Clean-up environment
//
//    } catch (SQLException e) {
//
//      e.printStackTrace();
//
//    } catch (Exception e) {
//
//      e.printStackTrace();
//
//    }
//    //Create a ResultSet object to hold the data from the executed query
//
//    //Use the MetaData from the ResultSet to append the column names to the text area
//
//    //Use a while loop to display the values of the returned data to the test area
//  }
}
