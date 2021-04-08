package com.turkcell.poc.model;

import com.turkcell.poc.constant.LineStatus;
import com.turkcell.poc.constant.LineType;
import com.turkcell.poc.constant.PaymentType;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductCreateDto implements Serializable {

  private static final long serialVersionUID = -3125893136898680731L;

  private String gsmNumber;

  private String customerName;

  private LineType lineType;

  private LineStatus lineStatus;

  private PaymentType paymentType;

  private String shortNumber;

}
