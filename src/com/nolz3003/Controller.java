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
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class Controller {

  private static Connection conn;

  @FXML
  private ChoiceBox<String> itemTypeChoice;

  @FXML
  private TextField productNameField;

  @FXML
  private TextField manufacturerField;

  @FXML
  private TableView<Product> existingProductsTable = new TableView<>();

  @FXML
  private TableColumn<Widget, String> productNameColumn;

  @FXML
  private TableColumn<Widget, String> manufacturerColumn;

  @FXML
  private TableColumn<Widget, String> itemTypeColumn;

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

  private String itemTypeCode = null;

  /*
   * Credit for the editable cell implementation goes to James_D on StackOverflow
   * https://stackoverflow.com/questions/28414825/make-individual-cell-editable-in-javafx-tableview
   */
  private PseudoClass editableCssClass = PseudoClass.getPseudoClass("editable");
  private Callback<TableColumn<Widget, String>, TableCell<Widget, String>> defaultTextFieldCellFactory
      = TextFieldTableCell.forTableColumn();

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
      existingProductsTable.setEditable(true);

      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      populateExistingProducts(rs);
      existingProductsTable.setItems(existingProducts);

      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {

      for (ItemType it : ItemType.values()) {
        itemTypeChoice.getItems().add(it.toString() + " = " + it.getItemTypeCode());
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    //Allows the user to add an entry
    quantity.setEditable(true);

    // Show 1 as the default value.
    quantity.getSelectionModel().selectFirst();

//    productNameColumn.setCellFactory(col -> {
//      TableCell<Widget, String> cell = defaultTextFieldCellFactory.call(col);
//      cell.itemProperty().addListener((obs, oldValue, newValue) -> {
//        TableRow row = cell.getTableRow();
//        if (row == null) {
//          cell.setEditable(false);
//        } else {
//          Product product = (Widget) cell.getTableRow().getItem();
//          if (product == null) {
//            cell.setEditable(false);
//          } else {
//            cell.setEditable(true);
//          }
//          cell.pseudoClassStateChanged(editableCssClass, cell.isEditable());
//        }
//        cell.pseudoClassStateChanged(editableCssClass, cell.isEditable());
//      });
//      return cell;
//    });
  }

  @FXML
  private void displaySelectedProduct(MouseEvent event) {

    Product selectedProduct = existingProductsTable.getSelectionModel().getSelectedItem();
    if (selectedProduct != null) {
      System.out.println(selectedProduct.getProductName());
    }
  }

//  private void selectProduct() {
//    clear();
//    selectedProduct = existingProductsTable.getSelectionModel().getSelectedItem();
//   existingProductsTable.getSelectionModel().select(selectedProduct);
//  }
//
//  private void clear() {
//    selectedProduct = null;
//    existingProductsTable.getSelectionModel().clearSelection();
//  }

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
        && itemTypeChoice.getValue() != null) {

      String addProductString = "INSERT INTO PRODUCT(NAME, MANUFACTURER, TYPE) VALUES (?,?,?)";
      String showProductsString = "SELECT NAME,MANUFACTURER,TYPE FROM PRODUCT";

      // Prepared statements used to add a product to the database and to query the existing products
      PreparedStatement addProduct;
      PreparedStatement showProducts;

      String enteredProductName = productNameField.getText();
      productNameField.clear();
      String enteredManufacturer = manufacturerField.getText();
      manufacturerField.clear();
      String enteredItemType = itemTypeChoice.getValue();

      switch (enteredItemType) {
        case "Audio = AU":
          itemTypeCode = ItemType.Audio.getItemTypeCode();
          break;
        case "Visual = VI":
          itemTypeCode = ItemType.Visual.getItemTypeCode();
          break;
        case "AudioMobile = AM":
          itemTypeCode = ItemType.AudioMobile.getItemTypeCode();
          break;
        case "VisualMobile = VM":
          itemTypeCode = ItemType.VisualMobile.getItemTypeCode();
          break;
        default:
          break;
      }
      // Show 1 as the default value.
      itemTypeChoice.getSelectionModel().selectFirst();

      try {

        addProduct = conn.prepareStatement(addProductString);
        showProducts = conn.prepareStatement(showProductsString);
        addProduct.setString(1, enteredProductName);
        addProduct.setString(2, enteredManufacturer);
        addProduct.setString(3, itemTypeCode);

        //STEP 2: Register JDBC driver
        //Class.forName("com.mysql.jdbc.Driver");
        addProduct.executeUpdate();
        ResultSet rs = showProducts.executeQuery();

        populateExistingProducts(rs);

//        //FINALLY ADDED TO TableView
        existingProductsTable.setItems(existingProducts);

        rs.close();
        addProduct.close();
        showProducts.close();

      } catch (SQLException ex) {

        ex.printStackTrace();
      }
    }
  }

  private void populateExistingProducts(ResultSet rs) {

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
        String itemTypeCode = row.get(2);
        existingProducts.add(new Widget(productName, manufacturer, itemTypeCode));
        System.out.println("Row added " + row);
      }

      if (existingProducts.isEmpty()) {
        existingProducts.add(new Widget("", "", ""));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

//  private <S, T> TableColumn<S, T> createCol(String title,
//      Function<S, ObservableValue<T>> property) {
//    TableColumn<S, T> col = new TableColumn<>(title);
//    col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
//    return col;
//  }
}