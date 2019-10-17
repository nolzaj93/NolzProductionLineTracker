package com.nolz3003;

/**
 * The ItemType enum contains a full string for each item type and a respective item type code.
 */
public enum ItemType {

  Audio("AU"),
  Visual("VI"),
  AudioMobile("AM"),
  VisualMobile("VM");

  private String code;

  ItemType(String code) {
    this.code = code;
  }

  void setItemTypeCode(String code) {
    this.code = code;
  }

  public String getItemTypeCode() {
    return this.code;
  }

}
