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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

  private static Connection conn;

  @FXML
  private ComboBox<String> itemTypeCombo;

  @FXML
  private TextField productNameField;

  @FXML
  private TextField manufacturerField;

  @FXML
  private static TableView<Product> existingProductsTable = new TableView<>();

  @FXML
  private ComboBox<String> quantity;

  @FXML
  private Statement stmt;

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

  private final ObservableList<Product> existingProducts = FXCollections.observableArrayList();

  /**
   * This method is called by default because this Controller class implements Initializable.
   */
  public void initialize() {

    initializeDB();

    productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

    itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));

    //existingProductsTable.getColumns().addAll(productNameColumn, manufacturerColumn,
    // itemTypeColumn);

    //existingProducts = FXCollections.observableArrayList();
    // existingProductsTable = new TableView<>();

    // Populates the comboBox named quantity with numbers 1-10
    for (int count = 1; count <= 10; count++) {
      quantity.getItems().add(Integer.toString(count));
    }

    try {
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
        System.out.println("Row added " + row);
        String productName = row.get(0);
        String manufacturer = row.get(1);
        String itemType = row.get(2);

        existingProducts.add(new Product(productName, manufacturer, itemType));
      }
      rs.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    // Must figure out why existingProductsTable will not show the Products in existingProducts
    // These are member methods of TableView
    //existingProductsTable.setItems(existingProducts);
    //existingProductsTable.getColumns().addAll(productNameColumn,manufacturerColumn,itemTypeColumn)
    //existingProductsTable.getItems().addAll(existingProducts);
    //existingProductsTable.refresh();
    existingProductsTable.setItems(existingProducts);

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
      String showProductsString = "SELECT NAME,TYPE,MANUFACTURER FROM PRODUCT";

      final PreparedStatement addProduct = conn.prepareStatement(addProductString);
      final PreparedStatement showProducts = conn.prepareStatement(showProductsString);

      String productName = productNameField.getText();
      productNameField.clear();
      String manufacturer = manufacturerField.getText();
      manufacturerField.clear();
      addProduct.setString(1, productName);
      addProduct.setString(2, manufacturer);
      String itemType = itemTypeCombo.getValue();
      // Show 1 as the default value.
      itemTypeCombo.getSelectionModel().selectFirst();
      addProduct.setString(3, itemType);

      try {
        //STEP 2: Register JDBC driver
        //Class.forName("com.mysql.jdbc.Driver");
        addProduct.executeUpdate();
        ResultSet rs = showProducts.executeQuery();

        while (rs.next()) {
          //Iterate Row
          ObservableList<String> row = FXCollections.observableArrayList();
          for (int i = 1; i <= 3; i++) {
            //Iterate Column
            row.add(rs.getString(i));

          }
          existingProducts.add(new Product(productName, manufacturer, itemType));
          System.out.println("Row added " + row);

        }

        //FINALLY ADDED TO TableView
        existingProductsTable.setItems(existingProducts);

      } catch (Exception e) {

        e.printStackTrace();
      } finally {
        addProduct.close();
        showProducts.close();
      }
    }
  }
}