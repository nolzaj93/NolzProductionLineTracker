package com.nolz3003;

import javafx.beans.property.SimpleStringProperty;

/**
 * The Item interface is implemented by the Product class to ensure all the methods below are
 * included after the Product class.
 */
public interface Item {

  int getId();

  String getProductName();

  SimpleStringProperty productNameProperty();

  void setProductName(String productName);

  String getManufacturer();

  SimpleStringProperty manufacturerProperty();

  void setManufacturer(String manufacturer);

  String getItemTypeCode();

  SimpleStringProperty itemTypeProperty();

  void setItemTypeCode(String itemTypeCode);

  SimpleStringProperty valueProperty();

  String getValue();
}
