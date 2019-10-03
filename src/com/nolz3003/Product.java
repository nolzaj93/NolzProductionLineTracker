/**
 * The Product class holds the data for each product added to the Existing Product table. Date:
 * 9/28/19
 *
 * @author Austin Nolz
 */

package com.nolz3003;

import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("unused")
public abstract class Product implements Item {

  private SimpleStringProperty productName = new SimpleStringProperty("");
  private SimpleStringProperty manufacturer = new SimpleStringProperty("");
  private SimpleStringProperty itemType = new SimpleStringProperty("");
  private int id;
  private static int productionNumber;

  public Product() {
    this("", "", "");
  }

  public Product(String productName, String manufacturer, String itemType) {
    //set Product Name, Manufacturer, and itemType
    setProductName(productName);
    setManufacturer(manufacturer);
    setItemType(itemType);
    id = ++productionNumber;
  }

  public String toString() {
    return "Name: " + productName + "\n"
        + "Manufacturer: " + manufacturer + "\n"
        + "Type: " + itemType;
  }

  public int getId() {

    return id;
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

  private void setItemType(String itemType) {
    this.itemType.set(itemType);
  }

  public final SimpleStringProperty valueProperty() {
    return this.productName;
  }

  public final String getValue() {
    return this.valueProperty().get();
  }

}
