package com.nolz3003;

import java.util.Date;

/**
 * ProductionRecord class.
 *
 * @author austinnolz - The ProductionRecord class holds data about each unit produced and tracked.
 */
public class ProductionRecord {

  private static int currentProdNum = 1;
  private static int currentAUSerial = 0;
  private static int currentVISerial = 0;
  private static int currentAMSerial = 0;
  private static int currentVMSerial = 0;

  //Unique for every item produced auto incremented by DB
  private int productionNum;

  //productID from Product table/class
  private int productID;

  private String serialNum;

  private Date prodDate;

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
    setProdDate(new Date());

  }

  /**
   * ProductionRecord constructor accepting a production number, productID, serial number, and
   * production date.
   *
   * @param product - the product object
   * @param productionNum - unique item unit number
   * number incremented for that type.
   * @param prodDate - Date the unit was produced.
   */
  public ProductionRecord(Product product, int productionNum,
      Date prodDate) {

    this.productID = product.getId();
    this.productionNum = productionNum;
    this.prodDate = prodDate;

    setProductionNum(currentProdNum++);

    String productType = product.getItemTypeCode();
    switch (productType) {
      case "AU":
        setSerialNum(product.getManufacturer().substring(0, 3) + "AU" + currentAUSerial++);

        break;
      case "VI":
        setSerialNum(product.getManufacturer().substring(0, 3) + "VI" + currentVISerial++);

        break;
      case "AM":
        setSerialNum(product.getManufacturer().substring(0, 3) + "AM" + currentAMSerial++);

        break;
      case "VM":
        setSerialNum(product.getManufacturer().substring(0, 3) + "VM" + currentVMSerial++);

        break;
    }
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
    return new Date(prodDate.getTime());
  }

  public void setProdDate(Date prodDate) {
    this.prodDate = new Date(prodDate.getTime());
  }

  public static int getCurrentAUSerial() {
    return currentAUSerial;
  }

  public static void setCurrentAUSerial(int currentAUSerial) {
    ProductionRecord.currentAUSerial = currentAUSerial;
  }

  public static int getCurrentVISerial() {
    return currentVISerial;
  }

  public static void setCurrentVISerial(int currentVISerial) {
    ProductionRecord.currentVISerial = currentVISerial;
  }

  public static int getCurrentAMSerial() {
    return currentAMSerial;
  }

  public static void setCurrentAMSerial(int currentAMSerial) {
    ProductionRecord.currentAMSerial = currentAMSerial;
  }

  public static int getCurrentVMSerial() {
    return currentVMSerial;
  }

  public static void setCurrentVMSerial(int currentVMSerial) {
    ProductionRecord.currentVMSerial = currentVMSerial;
  }

  public static int getCurrentProdNum() {
    return currentProdNum;
  }

  public static void setCurrentProdNum(int currentProdNum) {
    ProductionRecord.currentProdNum = currentProdNum;
  }

  @Override
  public String toString() {
    return "Prod. Num: " + getProductionNum() + " Product ID: " + getProductID()
        + " Serial Num: " + getSerialNum() + " Date: " + getProdDate() + "\n";
  }
}
