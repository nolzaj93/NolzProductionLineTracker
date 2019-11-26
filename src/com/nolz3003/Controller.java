package com.nolz3003;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * The Controller class which observes the menu.fxml view.
 *
 * @author Austin Nolz The Controller class listens for user events and updates the view using the
 *     Product class. This Controller contains methods to initialize the database, initialize
 *     comboBox members, add new products to a database, and display that data to a TableView.
 */
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
  private ComboBox<Integer> quantityCombo;

  @FXML
  private Statement stmt;

  @FXML
  private TextArea pLogTextArea;

  @FXML
  private ListView productListView;

  @FXML
  private Button recordBtn;

  private ObservableList<Product> productLine;

  private ObservableList<Product> existingProducts;

  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:h2:./res/HR";
  //  Database credentials
  private static final String USER = "";
  private static  String PASS = "";

  private String itemTypeCode = null;

  /*
   * Credit for the editable cell implementation goes to James_D on StackOverflow
   * https://stackoverflow.com/questions/28414825/make-individual-cell-editable-in-javafx-tableview
   *
   * private PseudoClass editableCssClass = PseudoClass.getPseudoClass("editable");
   * private Callback<TableColumn<Widget, String>,
   * TableCell<Widget, String>> defaultTextFieldCellFactory
   *   = TextFieldTableCell.forTableColumn();
   */

  /**
   * This method is called by default because this Controller class implements Initializable.
   */
  public void initialize() {

    // Load counts of productionRecord and serial numbers of each type.

    //Display the production record in the TextArea on the Production Log tab.

    // Credit: http://tutorials.jenkov.com/javafx/listview.html
    //  listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    productListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    recordBtn.setOnAction(event -> {
      ObservableList<Product> selectedProducts = productListView.getSelectionModel().getSelectedItems();

      recordProduction(selectedProducts);

    });

    initializeDB();

    existingProducts = FXCollections.observableArrayList();
    // existingProductsTable = new TableView<>();

    // Populates the comboBox named quantity with numbers 1-10
    for (int count = 1; count <= 10; count++) {
      quantityCombo.getItems().add(count);
    }
    String sql = "SELECT ID,NAME,MANUFACTURER,TYPE FROM PRODUCT";

    try {
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
    //Allows the user to add an entry.
    quantityCombo.setEditable(true);

    // Show 1 as the default value.
    quantityCombo.getSelectionModel().selectFirst();

    //Populate ListView
    productListView.setItems(existingProducts);

  }

  /**
   * Highlights the selected product in the existing products table.
   */
  @FXML
  private void displaySelectedProduct() {

    Product selectedProduct = existingProductsTable.getSelectionModel().getSelectedItem();
    if (selectedProduct != null) {
      System.out.println(selectedProduct.getProductName());
    }
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

    try {
      Properties prop = new Properties();
      prop.load(new FileInputStream("res/properties"));
      PASS = prop.getProperty("password");

    } catch (IOException ex) {
      ex.printStackTrace();
    }

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
   * This method adds new products to the database and updates the TableView.
   */
  @FXML
  protected void addNewProduct() {

    if (!productNameField.getText().isEmpty() && !manufacturerField.getText().isEmpty()
        && itemTypeChoice.getValue() != null) {

      String enteredProductName = productNameField.getText();

      for (Product product : existingProducts) {
        if (product.getProductName().equals(enteredProductName)) {
          //Set Error message visible (Product already exists)
          return;
        }
      }

      String addProductString = "INSERT INTO PRODUCT(NAME, MANUFACTURER, TYPE) VALUES (?,?,?)";
      productNameField.clear();
      String enteredManufacturer = manufacturerField.getText();
      manufacturerField.clear();
      String enteredItemType = itemTypeChoice.getValue();

      ItemType it = null;
      switch (enteredItemType) {
        case "AUDIO = AU":
          itemTypeCode = ItemType.AUDIO.getItemTypeCode();
          it = ItemType.AUDIO;
          break;
        case "VISUAL = VI":
          itemTypeCode = ItemType.VISUAL.getItemTypeCode();
          it = ItemType.VISUAL;
          break;
        case "AUDIOMOBILE = AM":
          itemTypeCode = ItemType.AUDIOMOBILE.getItemTypeCode();
          it = ItemType.AUDIOMOBILE;
          break;
        case "VISUALMOBILE = VM":
          itemTypeCode = ItemType.VISUALMOBILE.getItemTypeCode();
          it = ItemType.VISUALMOBILE;
          break;
        default:
          it = ItemType.AUDIO;
          break;
      }
      // Show 1 as the default value.
      itemTypeChoice.getSelectionModel().selectFirst();

      //Prepared statements used to add a product to the database and to query the existing products
      PreparedStatement addProduct;

      try {

        addProduct = conn.prepareStatement(addProductString);
        addProduct.setString(1, enteredProductName);
        addProduct.setString(2, enteredManufacturer);
        addProduct.setString(3, itemTypeCode);

        addProduct.executeUpdate();
        existingProducts.add(new Widget((productLine.size()+1),enteredProductName, enteredManufacturer, it));

        addProduct.close();

      } catch (SQLException ex) {

        ex.printStackTrace();
      }
    }
  }

  /**
   * This method populates the TableView with the updated data from the database.
   *
   * @param rs - The result set returned from the query returns the name, manufacturer and type for
   *     each row.
   */
  private void populateExistingProducts(ResultSet rs) {

    try {

      while (rs.next()) {
        //Iterate Row
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int count = 1; count <= 4; count++) {
          row.add(rs.getString(count));
        }
        int id = Integer.parseInt(row.get(0));
        String productName = row.get(1);
        String manufacturer = row.get(2);
        String itemType = row.get(3);
        ItemType it;
        switch (itemType) {
          case "AU":
            it = ItemType.AUDIO;
            break;
          case "VI":
            it = ItemType.VISUAL;
            break;
          case "AM":
            it = ItemType.AUDIOMOBILE;
            break;
          case "VM":
            it = ItemType.VISUALMOBILE;
            break;
          default:
            it = ItemType.AUDIO;
            break;
        }

        existingProducts.add(new Widget(id, productName, manufacturer, it));
        System.out.println("Row added " + row);
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * A method to test the MultimediaController interface.
   */
  private static void testMultimedia() {

    AudioPlayer newAudioProduct = new AudioPlayer(1,"DP-X1A", "Onkyo",

        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC",
        "M3U/PLS/WPL");

    Screen newScreen = new Screen("720x480", 40, 22);

    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101",
        "OracleProduction", newScreen, MonitorType.LCD);

    ArrayList<MultimediaControl> productList = new ArrayList<>();

    productList.add(newAudioProduct);

    productList.add(newMovieProduct);

    for (MultimediaControl p : productList) {

      System.out.println(p);

      p.play();

      p.stop();

      p.next();

      p.previous();

    }
  }

  public void recordProduction(ObservableList<Product> selectedProducts){

    // Utilize a for loop which iterates until the number taken from the quantity ComboBox is reached
    // On each iteration construct a ProductionRecord object
    // --Call toString for each object and append the text to the pLogTextArea
    // --Add row to database for each iteration

    for(Product selectedProduct : selectedProducts){

      // Create and Add ProductionRecord objects from database ResultSet to the pLogTextArea

      int productQuantity = quantityCombo.getValue();
      for(int productCount = 0; productCount < productQuantity; productCount++) {
        ProductionRecord currentRecord = new ProductionRecord(selectedProduct, ProductionRecord.getCurrentProdNum(), new Date());

        pLogTextArea.appendText(currentRecord.toString());
      }

    }
  }
}