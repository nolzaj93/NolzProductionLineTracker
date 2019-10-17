package com.nolz3003;

import javafx.beans.property.SimpleStringProperty;

/**
 * The Product class.
 *
 * @author Austin Nolz
 *
 *      The Product class holds the data for each product added to the Existing Product table.
 */
@SuppressWarnings("unused")
public abstract class Product implements Item {

  private SimpleStringProperty productName = new SimpleStringProperty("");
  private SimpleStringProperty manufacturer = new SimpleStringProperty("");
  private SimpleStringProperty itemTypeCode = new SimpleStringProperty("");

  private int id;
  private String itemType;

  public Product() {
    this("", "", "");
  }

  /**
   * Constructor for the Product class.
   *
   * @param productName - product name
   * @param manufacturer - manufacturer
   * @param itemTypeCode - item type code
   */
  public Product(String productName, String manufacturer, String itemTypeCode) {
    //set Product Name, Manufacturer, and itemType
    setProductName(productName);
    setManufacturer(manufacturer);
    setItemTypeCode(itemTypeCode);
  }

  /**
   * Overridden toString() method, which prints the name, manufacturer, type of the Product object.
   *
   * @return - returns the String containing the product information.
   */
  @Override
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

  public void setItemTypeCode(String itemTypeCode) {
    this.itemTypeCode.set(itemTypeCode);
  }

  public final SimpleStringProperty valueProperty() {
    return this.productName;
  }

  public final String getValue() {
    return this.valueProperty().get();
  }

  public String getItemType() {
    return itemType;
  }

  void setItemType(String itemType) {
    this.itemType = itemType;
  }
}
