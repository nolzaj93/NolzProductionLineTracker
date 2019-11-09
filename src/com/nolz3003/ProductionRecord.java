package com.nolz3003;

import java.util.Date;

/**
 * ProductionRecord class.
 *
 * @author austinnolz
 *
 *      The ProductionRecord class holds data about each unit produced and tracked.
 */
public class ProductionRecord {

  //Unique for every item produced auto incremented by DB
  private int productionNum;

  //productID from Product table/class
  private int productID;

  private String serialNum;

  private Date prodDate;


  //  Issue 5 - Production enhancement
//
//  Add the ability for the program to generate a unique serial number for each produced product.
//
//  Overload the ProductionRecord constructor to accept a Product and an int which holds the count
//  of the number of items of its type that have been created. (You can write the code to generate
//  the count later.) Set the serialNumber to start with the first three letters of the
//  Manufacturer name, then the two letter ItemType code, then five digits
//  (with leading 0s if necessary) that are unique and sequential for the item type. The entire
//  Serial Number should be programmatically created and assigned.
//
//  Optional (for now) challenge: Show the product name instead of the product ID in the TextArea on the Production Log tab.
//
  public ProductionRecord(int productID) {
    this(0, productID, "0", new Date());
  }

  public ProductionRecord(Product product, int productionTypeCount) {
    String serial = product.getManufacturer().substring(0,3) + product.getItemTypeCode() + String.format("%05d",productionTypeCount);
    setSerialNum(serial);


  }
  public ProductionRecord(int productionNum, int productID, String serialNum,
      Date prodDate) {

    this.productID = productID;
    this.productionNum = productionNum;
    this.serialNum = serialNum;
    this.prodDate = prodDate;

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
    return prodDate;
  }

  public void setProdDate(Date prodDate) {
    this.prodDate = prodDate;
  }

  @Override
  public String toString() {
    return "Prod. Num: " + getProductionNum() + " Product ID: " + getProductID()
        + " Serial Num: " + getSerialNum() + " Date: " + getProdDate() + "\n";
  }
}
