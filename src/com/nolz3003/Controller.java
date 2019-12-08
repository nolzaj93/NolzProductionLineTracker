package com.nolz3003;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * The Controller class which observes the menu.fxml view.
 *
 * @author Austin Nolz - The Controller class listens for user events and updates the view using the
 * Product class. This Controller contains methods to initialize the database, initialize comboBox
 * members, add new products to a database, and display that data to a TableView.
 */
public class Controller {

  private Connection conn = null;

  @FXML
  private ChoiceBox<String> itemTypeChoice;

  @FXML
  private TextField productNameField;

  @FXML
  private TextField manufacturerField;

  @FXML
  private TableView<Product> existingProductsTable = new TableView<>();

  @FXML
  private ComboBox<String> quantityCombo;

  @FXML
  private Statement stmt = null;

  @FXML
  private TextArea pLogTextArea;

  @FXML
  private ListView<Product> productListView;

  @FXML
  private Button recordBtn;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField nameField;

  @FXML
  private TextField pwField;

  @FXML
  private TextArea employeeTA;

  @FXML
  private Label returnMsg;

  private ObservableList<Product> productLine;

  private ArrayList<ProductionRecord> productionRun;

  private ArrayList<ProductionRecord> productionLog;

  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:h2:./res/HR";
  //  Database credentials
  private static final String USER = "";
  private static String PASS = "";

  private String itemTypeCode = null;

  /**
   * This method is called by default because this Controller class implements Initializable.
   */
  public void initialize() throws IOException {

    testMultimedia();

    // Load counts of productionRecord and serial numbers of each type.

    //Display the production record in the TextArea on the Production Log tab.

    // Credit: http://tutorials.jenkov.com/javafx/listview.html
    //  listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    recordBtn.setOnAction(event -> {

      ObservableList<Product> selectedProducts = productListView.getSelectionModel()
          .getSelectedItems();

      if (selectedProducts != null) {
        recordProduction(selectedProducts);
      }
    });

    initializeDB();

    productLine = FXCollections.observableArrayList();
    // existingProductsTable = new TableView<>();

    // Populates the comboBox named quantity with numbers 1-10
    for (int count = 1; count <= 10; count++) {
      quantityCombo.getItems().add(Integer.toString(count));
    }
    String sql = "SELECT ID,NAME,MANUFACTURER,TYPE FROM PRODUCT";

    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      loadProductList(rs);
      existingProductsTable.setItems(productLine);

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
    productListView.setItems(productLine);

    //Load productionLog
    productionLog = new ArrayList<>();
    productionRun = new ArrayList<>();

    if (!productLine.isEmpty()) {
      loadProductionLog();
    }
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
  private void initializeDB() throws IOException {
    // intelliJ must be disconnected from the database in order for program to connect
    // Hit the red square to stop intelliJ connection to database before running the program
    // Right click on database, Diagrams, Open Visualization
    // Need a database diagram for the Production Line Project

    FileInputStream stream = null;
    try {
      Properties prop = new Properties();
      stream = new FileInputStream("res/properties");
      prop.load(stream);
      PASS = reverseString(prop.getProperty("password"));
      stream.close();


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

      for (Product product : productLine) {
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

      ItemType it;
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
        productLine
            .add(new Widget((productLine.size() + 1), enteredProductName, enteredManufacturer, it));

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
   * each row.
   */
  private void loadProductList(ResultSet rs) {

    try {

      while (rs.next()) {

        int id = rs.getInt(1);
        String productName = rs.getString(2);
        String manufacturer = rs.getString(3);
        String itemType = rs.getString(4);
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

        productLine.add(new Widget(id, productName, manufacturer, it));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * A method to test the MultimediaController interface.
   */
  private static void testMultimedia() {

    AudioPlayer newAudioProduct = new AudioPlayer(1, "DP-X1A", "Onkyo",

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

  /**
   * The recordProduction() method creates an arraylist of type ProductionRecord.
   *
   * @param selectedProducts - Products chosen from the productListView.
   */
  private void recordProduction(ObservableList<Product> selectedProducts)
      throws NumberFormatException {

    productionRun.clear();

    String quantityString = quantityCombo.getValue();
    try {
      Integer.parseInt(quantityString);
    } catch (NumberFormatException e) {

      errorLabel.setText("Please enter a number into the text box.");
      errorLabel.setVisible(true);
      quantityCombo.setValue("1");
      return;
    }

    int quantity = Integer.parseInt(quantityCombo.getValue());
    for (Product selectedProduct : selectedProducts) {

      // Create and Add ProductionRecord objects from database ResultSet to the pLogTextArea
      for (int productCount = 0; productCount < quantity; productCount++) {
        ProductionRecord currentRecord = new ProductionRecord(selectedProduct,
            ProductionRecord.getCurrentProdNum(), new Timestamp(System.currentTimeMillis()));

        productionRun.add(currentRecord);
      }
    }
    addToProductionDB();
    showProductionRun();
  }

  /**
   * Loads the productionLog ArrayList from the database and processes the productionNumber
   * productID, serialNumber, and date for each row in the PRODUCTIONRECORD table.
   */
  private void loadProductionLog() throws IOException {

    String sql = "SELECT PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED "
        + "FROM PRODUCTIONRECORD";
    FileInputStream stream = null;
    try {
      Properties prop = new Properties();
      stream = new FileInputStream("res/properties");
      prop.load(stream);
      stream.close();

      PASS = reverseString(prop.getProperty("password"));

      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {

        int prodNum = rs.getInt(1);
        int prodID = rs.getInt(2);
        String serialNum = rs.getString(3);
        Date prodDate = new Date(rs.getTimestamp(4).getTime());

        ProductionRecord currentRecord = new ProductionRecord(productLine.get(prodID - 1),
            prodNum,
            serialNum, prodDate);

        productionLog.add(currentRecord);
      }
      rs.close();

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    showProductionLog();

  }

  /**
   * Appends the production record information from the toString() method for each ProductionRecord
   * object within the productionRun list.
   */
  public void showProductionRun() {
//    populate the TextArea on the Production Log tab with the information from the productionRun,
//    replacing the productId with the product name, with one line for each product produced

    for (ProductionRecord currentRecord : productionRun) {

      pLogTextArea.appendText(currentRecord.toString());
    }
  }

  /**
   * Appends the production record information from the toString() method for each ProductionRecord
   * object within the productionLog list.
   */
  public void showProductionLog() {
//    populate the TextArea on the Production Log tab with the information from the productionRun,
//    replacing the productId with the product name, with one line for each product produced

    for (ProductionRecord currentRecord : productionLog) {

      pLogTextArea.appendText(currentRecord.toString());
    }
  }

  /**
   * Adds a row to the ProductionRecord table for each ProductionRecord object in the productionRun
   * list.
   */
  public void addToProductionDB() {

    for (ProductionRecord record : productionRun) {

      String addRecordString =
          "INSERT INTO PRODUCTIONRECORD(PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) "
              + "VALUES (?,?,?,?)";
      PreparedStatement addRecord;

      try {

        addRecord = conn.prepareStatement(addRecordString);

        addRecord.setInt(1, record.getProductionNum());
        addRecord.setInt(2, record.getProductID());
        addRecord.setString(3, record.getSerialNum());
        addRecord.setTimestamp(4, new Timestamp(record.getProdDate().getTime()));
        addRecord.executeUpdate();

        addRecord.close();
      } catch (SQLException ex) {

        ex.printStackTrace();
      }
    }
  }

  /**
   * The reverseString method reverses strings.
   *
   * @param id - string to be reversed.
   */
  public String reverseString(String id) {
    // Paste the code for your reverseString method here.

    int strLen = id.length();

    //Base case returns the last letter, the only letter if id is 1 character at start
    if (strLen == 1) {
      return id;
    } else {
      String shortStr = id.substring(0, strLen - 1);
      //Recursive case returns the last character of the String
      // first and concatenates the result of the next call
      return id.charAt(strLen - 1) + reverseString(shortStr);
    }
  }

  /**
   * Takes the full name from the nameField and the pw from the pwField and if both have strings,
   * then an Employee object is created with the entered name and pw. The employee information is
   * set to the employeeTA text area.
   */
  @FXML
  public void createAccount() {
    String name = nameField.getText();
    String pw = pwField.getText();
    if (!name.isEmpty() && !pw.isEmpty()) {
      Employee newEmployee = new Employee(nameField.getText(), pwField.getText());
      employeeTA.setText(newEmployee.toString());
    } else {
      returnMsg.setText(
          "Please enter a full name separated by a space and a password with an uppercase,\n"
              + "lowercase, and special character.");
      returnMsg.setVisible(true);
    }
  }
}