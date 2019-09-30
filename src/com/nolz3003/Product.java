/**
 * The Product class holds the data for each product added to the Existing Product table.
 * Date: 9/28/19
 * @author Austin Nolz
 */

package com.nolz3003;

import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("unused")
public class Product {

  private final SimpleStringProperty productName = new SimpleStringProperty("");
  private final SimpleStringProperty manufacturer = new SimpleStringProperty("");
  private final SimpleStringProperty itemType = new SimpleStringProperty("");

  public Product() {
    this("", "", "");
  }

  public Product(String productName, String manufacturer, String itemType) {
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

  private void setProductName(String productName) {
    this.productName.set(productName);
  }

  public String getManufacturer() {
    return manufacturer.get();
  }

  public SimpleStringProperty manufacturerProperty() {
    return manufacturer;
  }

  private void setManufacturer(String manufacturer) {
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

}