/**
 * The Controller class listens for user events and updates the view using the Product class. This
 * Controller contains methods to initialize the database, initialize comboBox members, add new
 * products to a database, and display that data to a TableView. Date: 9/28/19
 *
 * @author Austin Nolz
 */

package com.nolz3003;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {

  private static Connection conn;

  @FXML
  private ChoiceBox<String> itemTypeCombo;

  @FXML
  private TextField productNameField;

  @FXML
  private TextField manufacturerField;

  @FXML
  private TableView<Product> existingProductsTable = new TableView<>();

  @FXML
  private ComboBox<String> quantity;

  @FXML
  private Statement stmt;

  private ObservableList<Product> existingProducts;

  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:h2:./res/HR;DB_CLOSE_DELAY=-1";

  //  Database credentials
  private static final String USER = "";
  private static final String PASS = "";

  /**
   * This method is called by default because this Controller class implements Initializable.
   */
  public void initialize() {

    initializeDB();

    existingProducts = FXCollections.observableArrayList();
    // existingProductsTable = new TableView<>();

    // Populates the comboBox named quantity with numbers 1-10
    for (int count = 1; count <= 10; count++) {
      quantity.getItems().add(Integer.toString(count));
    }
    String sql = "SELECT NAME,MANUFACTURER,TYPE FROM PRODUCT";

    try {

      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      populateExistingProducts(rs);
      existingProductsTable.setEditable(true);
      existingProductsTable.setItems(existingProducts);

      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // Populate Item Type ComboBox
      itemTypeCombo.getItems().add("AUDIO");
      itemTypeCombo.getItems().add("VIDEO");
      itemTypeCombo.getItems().add("AUDIOMOBILE");
      itemTypeCombo.getItems().add("VIDEOMOBILE");

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    //Allows the user to add an entry
    quantity.setEditable(true);

    // Show 1 as the default value.
    quantity.getSelectionModel().selectFirst();
  }


  /**
   * This method makes an attempt to connect to the H2 local database.
   */
  private void initializeDB() {
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

    } catch (ClassNotFoundException | SQLException e) {

      e.printStackTrace();
    }
  }

  /**
   * This method adds new products to the database and updates the TableView .
   *
   * @param event When the user clicks the Add Product button this method is passed an event
   */
  @FXML
  protected void addNewProduct(ActionEvent event) throws SQLException {

    if (!productNameField.getText().isEmpty() && !manufacturerField.getText().isEmpty()
        && itemTypeCombo.getValue() != null) {

      String addProductString = "INSERT INTO PRODUCT(NAME, MANUFACTURER, TYPE) VALUES (?,?,?)";
      String showProductsString = "SELECT NAME,MANUFACTURER,TYPE FROM PRODUCT";

      PreparedStatement addProduct = null;
      PreparedStatement showProducts = null;

      String enteredProductName = productNameField.getText();
      productNameField.clear();
      String enteredManufacturer = manufacturerField.getText();
      manufacturerField.clear();
      String enteredItemType = itemTypeCombo.getValue();
      // Show 1 as the default value.
      itemTypeCombo.getSelectionModel().selectFirst();

      try {

        addProduct = conn.prepareStatement(addProductString);
        showProducts = conn.prepareStatement(showProductsString);
        addProduct.setString(1, enteredProductName);
        addProduct.setString(2, enteredManufacturer);
        addProduct.setString(3, enteredItemType);

        //STEP 2: Register JDBC driver
        //Class.forName("com.mysql.jdbc.Driver");
        addProduct.executeUpdate();
        ResultSet rs = showProducts.executeQuery();

        populateExistingProducts(rs);

//        //FINALLY ADDED TO TableView
//        existingProductsTable.setItems(existingProducts);

        rs.close();
        addProduct.close();
        showProducts.close();

      } catch (SQLException ex) {

        ex.printStackTrace();
      }

    }
  }

  protected void populateExistingProducts(ResultSet rs) {

    try {

        existingProducts.clear();
        while (rs.next()) {
          //Iterate Row
          ObservableList<String> row = FXCollections.observableArrayList();
          for (int i = 1; i <= 3; i++) {
            row.add(rs.getString(i));
          }

          String productName = row.get(0);
          String manufacturer = row.get(1);
          String itemType = row.get(2);
          existingProducts.add(new Product(productName, manufacturer, itemType));
          System.out.println("Row added " + row);
        }

      if (existingProducts.isEmpty()) {
        existingProducts.add(new Product("", "", ""));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}