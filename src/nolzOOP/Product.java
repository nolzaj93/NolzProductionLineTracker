package nolzOOP;

import javafx.beans.property.SimpleStringProperty;

/**
 * The Product class holds the data for each product added to the Existing Product table.
 *
 */
public class Product {

  private final SimpleStringProperty productName = new SimpleStringProperty("");
  private final SimpleStringProperty manufacturer = new SimpleStringProperty("");
  private final SimpleStringProperty itemType = new SimpleStringProperty("");

  public Product() {this("","","");}

  public Product(String productName, String manufacturer, String itemType){
    //set Product Name, Manufacturer, and itemType
    setProductName(productName);
    setManufacturer(manufacturer);
    setItemType(itemType);
  }

  public String getProductName() {
    return productName.get();
  }

  public SimpleStringProperty productNameProperty() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName.set(productName);
  }

  public String getManufacturer() {
    return manufacturer.get();
  }

  public SimpleStringProperty manufacturerProperty() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer.set(manufacturer);
  }

  public String getItemType() {
    return itemType.get();
  }

  public SimpleStringProperty itemTypeProperty() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType.set(itemType);
  }
}
