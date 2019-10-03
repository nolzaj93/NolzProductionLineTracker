package com.nolz3003;

public class NewItem extends Product {
  public NewItem(){
    this("","","");
  }

  public NewItem(String prodName, String manufacturer, String itemType){
    super(prodName,manufacturer,itemType);
  }

}
