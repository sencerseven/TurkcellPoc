package com.turkcell.poc.constant;

public enum LineStatus {

  ACTIVE("ACTIVE"),
  PASSIVE("PASSIVE");

  private String value;

  LineStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
