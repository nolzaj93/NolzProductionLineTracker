package com.nolz3003;

/**
 * The ItemType enum contains a full string for each item type and a respective item type code.
 */
public enum ItemType {

  AUDIO("AU"),
  VISUAL("VI"),
  AUDIOMOBILE("AM"),
  VISUALMOBILE("VM");


  private String code;

  ItemType(String code) {
    this.code = code;
  }

  public String getItemTypeCode() {
    return this.code;
  }

  public String getCode() {
    return code;
  }
}
