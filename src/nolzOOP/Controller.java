package nolzOOP;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller implements Initializable {

  private NolzOOPProductionLineTracker app;
  private static Connection conn;

  @FXML
  private ComboBox itemTypeCombo;

  @FXML
  private TextField productNameField;

  @FXML
  private TextField manufacturerField;

  @FXML
  private static TableView<Product> existingProductsTable = new TableView<>();

  @FXML
  private ComboBox quantity;

  @FXML
  private static Statement stmt;

  @FXML
  private TableColumn<Product, String> productNameColumn;

  @FXML
  private TableColumn<Product, String> manufacturerColumn;

  @FXML
  private TableColumn<Product, String> itemTypeColumn;

  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:h2:./res/HR;DB_CLOSE_DELAY=-1";

  //  Database credentials
  private static final String USER = "";
  private static final String PASS = "";

  private ObservableList<Product> existingProducts = FXCollections.observableArrayList();

  public void initialize(URL url, ResourceBundle resourceBundle) {

    System.out.println("test");
    initializeDB();
//
//    productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
//
//    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));
//
//    itemTypeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("itemType"));

    //existingProducts = FXCollections.observableArrayList();
   // existingProductsTable = new TableView<>();
//  existingProductsTable.setPlaceholder(new Label("No rows to display"));

    // Populates the comboBox named quantity with numbers 1-10
    for (int count = 1; count <= 10; count++) {
      quantity.getItems().add(count);
    }

    try {
      //Populate existingProducts
      String sql = "SELECT NAME,TYPE,MANUFACTURER FROM PRODUCT";
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      /* Data added to ObservableList *
       ********************************/
      while (rs.next()) {
        //Iterate Row
        ObservableList<String> row = FXCollections.observableArrayList();

        for (int i = 1; i <= 3; i++) {
          //Iterate Column
          row.add(rs.getString(i));
        }
        System.out.println("Row [1] added " + row);

        existingProducts.add(new Product(row.get(0),row.get(1),row.get(2)));

      }
    }  catch (SQLException e) {
      e.printStackTrace();
    }
//    existingProductsTable.setItems(existingProducts);
//    existingProductsTable.getColumns().addAll(productNameColumn,manufacturerColumn,itemTypeColumn);
//    existingProductsTable.getItems().addAll(existingProducts);
   // existingProductsTable.refresh();
    existingProductsTable.setItems(getProductData());

    // Populate Item Type ComboBox
    itemTypeCombo.getItems().add("AUDIO");
    itemTypeCombo.getItems().add("VIDEO");
    itemTypeCombo.getItems().add("AUDIOMOBILE");
    itemTypeCombo.getItems().add("VIDEOMOBILE");

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

    conn = null;

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

    } catch (ClassNotFoundException e) {

      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
//      //STEP 2: Register JDBC driver
//      Class.forName("com.mysql.jdbc.Driver");
      stmt = conn.createStatement();

    } catch (SQLException e) {

      e.printStackTrace();

    } catch (Exception e) {

      e.printStackTrace();

    }
  }

  @FXML
  protected void addNewProduct(ActionEvent event) {
    if (existingProductsTable.getItems() != null) {
      existingProducts = existingProductsTable.getItems();
    } else if (productNameField.getText() == null || manufacturerField.getText() == null
        || itemTypeCombo.getValue() == null) {

      //Will eventually print Error Message, currently does nothing
    } else {

      String itemType = (String) itemTypeCombo.getValue();
      // Show 1 as the default value.
      itemTypeCombo.getSelectionModel().selectFirst();

      String productName = productNameField.getText();
      productNameField.clear();
      String manufacturer = manufacturerField.getText();
      manufacturerField.clear();

      try {
//      //STEP 2: Register JDBC driver
//      Class.forName("com.mysql.jdbc.Driver");
        stmt = conn.createStatement();
        String sql = "INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER) VALUES ('" + productName
            + "', '" + itemType + "', '" + manufacturer + "')";
        stmt.executeUpdate(sql);

        existingProducts = FXCollections.observableArrayList();

        sql = "SELECT NAME,TYPE,MANUFACTURER FROM PRODUCT";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);


        /* Data added to ObservableList *
         ********************************/
        while (rs.next()) {
          //Iterate Row
          ObservableList<String> row = FXCollections.observableArrayList();
          for (int i = 1; i <= 3; i++) {
            //Iterate Column
            row.add(rs.getString(i));
          }
          System.out.println("Row [1] added " + row);
          //existingProducts.add(row);

        }

        //FINALLY ADDED TO TableView
        existingProductsTable.setItems(existingProducts);


      } catch (SQLException e) {

        e.printStackTrace();

      } catch (Exception e) {

        e.printStackTrace();

      }
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

  public  ObservableList<Product> getProductData(){
    return existingProducts;
  }

}