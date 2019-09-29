/**
 * @author Austin Nolz
 * @brief The Controller class listens for user events and updates the view using data from models,
 * like Product.
 * Date: 9/28/19
 */
package com.nolz3003;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller implements Initializable {

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

  private final ObservableList<Product> existingProducts = FXCollections.observableArrayList();

  public void initialize(URL url, ResourceBundle resourceBundle) {

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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // Must figure out why existingProductsTable will not show the Products in existingProducts
    // These are member methods of TableView
    //existingProductsTable.setItems(existingProducts);
    //existingProductsTable.getColumns().addAll(productNameColumn,manufacturerColumn,itemTypeColumn)
    //existingProductsTable.getItems().addAll(existingProducts);
    //existingProductsTable.refresh();
    existingProductsTable.setItems(getProductData());

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

  private static void initializeDB() {
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

  @FXML
  protected void addNewProduct(ActionEvent event) {

    if (!productNameField.getText().isEmpty() && !manufacturerField.getText().isEmpty()
        && itemTypeCombo.getValue() != null) {

      String itemType = itemTypeCombo.getValue();
      // Show 1 as the default value.
      itemTypeCombo.getSelectionModel().selectFirst();

      String productName = productNameField.getText();
      productNameField.clear();

      String manufacturer = manufacturerField.getText();
      manufacturerField.clear();

      try {
        //STEP 2: Register JDBC driver
        //Class.forName("com.mysql.jdbc.Driver");
        stmt = conn.createStatement();
        String sql = "INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER) VALUES ('" + productName
            + "', '" + itemType + "', '" + manufacturer + "')";
        stmt.executeUpdate(sql);

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
          //existingProducts.add(row);
        }

        //FINALLY ADDED TO TableView
        existingProductsTable.setItems(existingProducts);

      } catch (Exception e) {

        e.printStackTrace();

      }
    }
  }

  private ObservableList<Product> getProductData() {
    return existingProducts;
  }

}