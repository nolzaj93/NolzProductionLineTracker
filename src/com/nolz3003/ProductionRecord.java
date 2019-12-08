package com.nolz3003;

import java.util.Date;

/**
 * The ProductionRecord class is a model for a ProductionRecord object.
 *
 * @author austinnolz - The ProductionRecord class holds data about each unit produced and tracked.
 * This class holds information about the current production number and serial numbers, and about
 * each item produced on the Production Line.
 */
public class ProductionRecord {

  private static int currentProdNum = 1;
  private static int currentAUSerial = 1;
  private static int currentVISerial = 1;
  private static int currentAMSerial = 1;
  private static int currentVMSerial = 1;

  private Product product;
  //Unique for every item produced auto incremented by DB
  private int productionNum;

  //productID from Product table/class
  private int productID;

  private String serialNum;

  private Date prodDate;

  /**
   * ProductionRecord constructor accepting a production number, productID, serial number, and
   * production date.
   *
   * @param product - the product object
   * @param productionNum - unique item unit number
   * @param serialNum - serial number
   * @param prodDate - date the unit was produced
   */
  ProductionRecord(Product product, int productionNum, String serialNum, Date prodDate) {
    this(product, productionNum, prodDate);
    setSerialNum(serialNum);
  }

  /**
   * ProductionRecord constructor accepting a Product object, productionNum, and production date.
   *
   * @param product - the product object
   * @param productionNum - unique item unit number
   * @param prodDate - Date the unit was produced.
   */
  ProductionRecord(Product product, int productionNum,
      Date prodDate) {
    setProduct(product);
    this.productID = product.getProductID();
    this.productionNum = productionNum;
    this.prodDate = prodDate;

    setProductionNum(currentProdNum++);

    String productType = product.getItemTypeCode();
    //Constructs a serial number depending on the ItemTypeCode
    switch (productType) {
      case "AU":
        setSerialNum(product.getManufacturer().substring(0, 3) + "AU" + String
            .format("%05d", currentAUSerial++));
        break;
      case "VI":
        setSerialNum(product.getManufacturer().substring(0, 3) + "VI" + String
            .format("%05d", currentVISerial++));
        break;
      case "AM":
        setSerialNum(product.getManufacturer().substring(0, 3) + "AM" + String
            .format("%05d", currentAMSerial++));
        break;
      case "VM":
        setSerialNum(product.getManufacturer().substring(0, 3) + "VM" + String
            .format("%05d", currentVMSerial++));
        break;
      default:
        System.out.println("Error: invalid item type code.");
        break;
    }
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  int getProductionNum() {
    return productionNum;
  }

  private void setProductionNum(int productionNum) {
    this.productionNum = productionNum;
  }

  int getProductID() {
    return productID;
  }

  String getSerialNum() {
    return serialNum;
  }

  private void setSerialNum(String serialNum) {
    this.serialNum = serialNum;
  }

  Date getProdDate() {
    return new Date(prodDate.getTime());
  }


  static int getCurrentProdNum() {
    return currentProdNum;
  }

  @Override
  public String toString() {
    return "Prod. Num: " + getProductionNum() + " Product ID: " + getProduct().getProductName()
        + " Serial Num: " + getSerialNum() + " Date: " + getProdDate() + "\n";
  }
}
