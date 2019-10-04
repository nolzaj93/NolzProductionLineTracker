package com.nolz3003;

public abstract class NewItem extends Product {

  public NewItem() {
    this("", "", "");
  }

  public NewItem(String prodName, String manufacturer, String itemTypeCode) {
    super(prodName, manufacturer, itemTypeCode);
  }

}
