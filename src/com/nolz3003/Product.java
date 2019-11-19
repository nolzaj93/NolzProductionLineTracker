package com.nolz3003;

/**
 * The Product class.
 *
 * @author Austin Nolz - The Product class holds the data for each product added to the
 *      Existing Product table.
 */
@SuppressWarnings("unused")
public abstract class Product implements Item {

  private String productName = "";
  private String manufacturer = "";
  private String itemTypeCode = "";
  private ItemType itemType;

  private int id;

  public Product() {
    this("", "", ItemType.AUDIO);
  }

  /**
   * Constructor for the Product class.
   *
   * @param productName - product name
   * @param manufacturer - manufacturer
   * @param itemType - item type
   */
  public Product(String productName, String manufacturer, ItemType itemType) {
    //set Product Name, Manufacturer, and itemType
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
        + "Item Type: "+ itemType.name() + "\n";
  }

  public int getId() {

    return id;
  }

  public ItemType getItemType() {
    return itemType;
  }

  public void setItemType(ItemType itemType) {
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


  public void setItemTypeCode(String itemTypeCode) {
    this.itemTypeCode = itemTypeCode;
  }

}
