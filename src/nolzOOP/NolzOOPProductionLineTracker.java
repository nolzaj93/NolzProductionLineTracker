package nolzOOP;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Austin Nolz
 *
 * -Description: required component of every Javadoc
 */
public class NolzOOPProductionLineTracker extends Application {

  private TextArea ta = new TextArea();
  private Button btShowJobs = new Button("Show Records");
  private ComboBox<String> cboTableName = new ComboBox<>();

  private Statement stmt;

  /**
   * The start method is the starting point of a JavaFX program. This start method sets the title.
   *
   * @param primaryStage The primary stage.
   * @throws Exception Any problem with the user input to be handled.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    initializeDB();

    //display the JOB Data
    btShowJobs.setOnAction(e -> showData());
    Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

    Scene scene = new Scene(root, 600, 420);
    primaryStage.setTitle("Display JOB Information");
    primaryStage.setScene(scene);
    scene.getStylesheets()
        .add(NolzOOPProductionLineTracker.class.getResource("styleCSS.css").toExternalForm());

    primaryStage.show();

//    primaryStage.setTitle("Hello World");
//    primaryStage.setScene(new Scene(root, 300, 275));
//    primaryStage.show();

  }

  private void initializeDB() {
    // intelliJ must be disconnected from the database in order for program to connect
// Hit the red square to stop intelliJ connection to database before running the program

// Right click on database, Diagrams, Open Visualization
// Need a database diagram for the Production Line Project
    final String JDBC_DRIVER = "org.h2.Driver";

    final String DB_URL = "jdbc:h2:./res/HR";

    //  Database credentials

    final String USER = "";

    final String PASS = "";

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

      DatabaseMetaData dbmd = conn.getMetaData();
      ResultSet rsTables = dbmd.getTables(null, null, "JOB%", new String[]{"TABLE"});

      while (rsTables.next()) {
        cboTableName.getItems().add(rsTables.getString("TABLE_NAME"));
      }

    } catch (ClassNotFoundException e) {

      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void showData() {
    ta.clear();
    String tableName = cboTableName.getValue();
    try {
      //Create query that will select from the chosen table name

      String sql = "SELECT * FROM " + tableName;

      ResultSet rs = stmt.executeQuery(sql);

      ResultSetMetaData rsmd = rs.getMetaData();

      int numberOfColumns = rsmd.getColumnCount();

      // Prints column names in text area
      for (int i = 1; i <= numberOfColumns; i++) {
        ta.appendText(rsmd.getColumnName(i) + " ");
      }
      ta.appendText("\n");

      while (rs.next()) {

        // Prints data for each column
        for (int i = 1; i <= numberOfColumns; i++) {
          ta.appendText(rs.getString(i) + " ");
        }
        ta.appendText("\n");
      }

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

  public static void main(String[] args) {

    launch(args);
  }
}
