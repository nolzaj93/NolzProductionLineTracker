/**
 * The Product class holds the data for each product added to the Existing Product table. Date:
 * 9/28/19
 *
 * @author Austin Nolz
 */

package com.nolz3003;

import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("unused")
public abstract class Product {

  private SimpleStringProperty productName = new SimpleStringProperty("");
  private SimpleStringProperty manufacturer = new SimpleStringProperty("");
  private SimpleStringProperty itemTypeCode = new SimpleStringProperty("");
  private int id;
  private static int productionNumber;

  public Product() {
    this("", "", "");
  }

  public Product(String productName, String manufacturer, String itemTypeCode) {
    //set Product Name, Manufacturer, and itemType
    setProductName(productName);
    setManufacturer(manufacturer);
    setItemTypeCode(itemTypeCode);
    id = ++productionNumber;
  }

  public String toString() {
    return "Name: " + productName + "\n"
        + "Manufacturer: " + manufacturer + "\n"
        + "Type: " + itemTypeCode;
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

  public String getItemTypeCode() {
    return itemTypeCode.get();
  }

  public SimpleStringProperty itemTypeProperty() {
    return itemTypeCode;
  }

  private void setItemTypeCode(String itemTypeCode) {
    this.itemTypeCode.set(itemTypeCode);
  }

  public final SimpleStringProperty valueProperty() {
    return this.productName;
  }

  public final String getValue() {
    return this.valueProperty().get();
  }

}
