package com.nolz3003;

import java.util.Date;

/**
 * ProductionRecord class.
 *
 * @author austinnolz - The ProductionRecord class holds data about each unit produced and tracked.
 */
public class ProductionRecord {

  //Unique for every item produced auto incremented by DB
  private int productionNum;

  //productID from Product table/class
  private int productID;

  private String serialNum;

  private Date prodDate;

  /**
   * ProductionRecord constructor which accepts a productID as a parameter.
   * @param productID - unique ID for a product in the product list.
   */
  public ProductionRecord(int productID) {
    this(0, productID, "0",
        new Date());
  }

  /**
   * ProductionRecord constructor which accepts a Product object and a productionTypeCount.
   *
   * @param product - Product object.
   * @param productionTypeCount - count of units produced of that type.
   */
  public ProductionRecord(Product product, int productionTypeCount) {
    String serial = product.getManufacturer().substring(0, 3) + product.getItemTypeCode() + String
        .format("%05d", productionTypeCount);
    setSerialNum(serial);

  }

  /**
   * ProductionRecord constructor accepting a production number, productID, serial number, and
   * production date.
   *
   * @param productionNum - unique item unit number
   * @param productID - ID number of the product in the product list.
   * @param serialNum - first three letters of the manufacturer name, item type code, and a 5 digit
   *      number incremented for that type.
   * @param prodDate - Date the unit was produced.
   */
  public ProductionRecord(int productionNum, int productID, String serialNum,
      Date prodDate) {

    this.productID = productID;
    this.productionNum = productionNum;
    this.serialNum = serialNum;
    this.prodDate = new Date();

  }

  public int getProductionNum() {
    return productionNum;
  }

  public void setProductionNum(int productionNum) {
    this.productionNum = productionNum;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public String getSerialNum() {
    return serialNum;
  }

  public void setSerialNum(String serialNum) {
    this.serialNum = serialNum;
  }

  public Date getProdDate() {
    return new Date();
  }

  public void setProdDate(Date prodDate) {
    this.prodDate = new Date(prodDate.getTime());
  }

  @Override
  public String toString() {
    return "Prod. Num: " + getProductionNum() + " Product ID: " + getProductID()
        + " Serial Num: " + getSerialNum() + " Date: " + getProdDate() + "\n";
  }
}
