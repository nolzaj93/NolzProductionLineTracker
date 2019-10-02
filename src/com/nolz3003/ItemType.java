package com.nolz3003;

public enum ItemType {

  Audio("AU"),
  Visual("VI"),
  AudioMobile("AM"),
  VisualMobile("VM");

  public String code;
  ItemType(String code) {
    this.code = code;
  }
}
