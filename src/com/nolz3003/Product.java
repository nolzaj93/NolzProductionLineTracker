package com.nolz3003;

/**
 * The Product class is a model for a Product object in the Product Line.
 *
 * @author austinnolz - The Product Class is a used to build a general Product object with a
 * productID, product Name, manufacturer, and item type code.
 */
public abstract class Product implements Item {

  private String productName = "";
  private String manufacturer = "";
  private String itemTypeCode = "";
  private ItemType itemType;

  private int productID;

  public Product() {
    this(0, "", "", ItemType.AUDIO);
  }

  /**
   * Constructor for the Product class which sets the productID, productName, manufacturer, and item
   * type.
   *
   * @param productID - productID number
   * @param productName - product name
   * @param manufacturer - manufacturer
   * @param itemType - item type
   */
  public Product(int productID, String productName, String manufacturer, ItemType itemType) {
    //set Product Name, Manufacturer, and itemType
    setProductID(productID);
    setProductName(productName);
    setManufacturer(manufacturer);
    setItemType(itemType);
    setItemTypeCode(itemType.getCode());

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
        + "Item Type: " + itemType.name() + "\n";
  }

  int getProductID() {
    return productID;
  }

  private void setProductID(int productID) {
    this.productID = productID;
  }

  void setItemType(ItemType itemType) {
    this.itemType = itemType;
  }

  @Override
  public String getProductName() {
    return productName;
  }

  @Override
  public void setProductName(String productName) {
    this.productName = productName;
  }

  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  @Override
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }


  public String getItemTypeCode() {
    return itemTypeCode;
  }

  private void setItemTypeCode(String itemTypeCode) {
    this.itemTypeCode = itemTypeCode;
  }

}
