package com.turkcell.poc.constant;

public enum PaymentType {

  CREDIT_CARD("CREDIT_CARD");

  private String value;

  PaymentType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
