package com.nolz3003;

/**
 * The Item interface is implemented by the Product class to ensure all the methods below are
 * included after the Product class.
 */
public interface Item {


  String getProductName();


  void setProductName(String productName);

  String getManufacturer();


  void setManufacturer(String manufacturer);

}
