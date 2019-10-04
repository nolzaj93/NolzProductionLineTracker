package com.nolz3003;

public enum ItemType {

  Audio("AU"),
  Visual("VI"),
  AudioMobile("AM"),
  VisualMobile("VM");

  private String code;

  ItemType(String code) {
    this.code = code;
  }

  public void setItemTypeCode(String code) {
    this.code = code;
  }

  public String getItemTypeCode() {
    return this.code;
  }

}
