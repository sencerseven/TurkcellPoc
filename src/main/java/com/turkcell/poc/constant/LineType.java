package com.turkcell.poc.constant;

public enum LineType {

  GSM2100("GSM2100"),
  GSM1900("GSM1900"),
  GSM1800("GSM1800"),
  GSM900("GSM900"),
  GSM850("GSM850");

  private String value;

  LineType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
