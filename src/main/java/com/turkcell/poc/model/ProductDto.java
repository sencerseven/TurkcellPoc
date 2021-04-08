package com.turkcell.poc.model;

import com.turkcell.poc.constant.LineStatus;
import com.turkcell.poc.constant.LineType;
import com.turkcell.poc.constant.PaymentType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto implements Serializable {

  private static final long serialVersionUID = 2833736222938990732L;

  @ApiModelProperty(notes = "The auto-generated ID of the product")
  private String id;

  @ApiModelProperty(notes = "The auto-generated ID of the product")
  private String gsmNumber;

  @ApiModelProperty(notes = "The customer name of the product")
  private String customerName;

  @ApiModelProperty(notes = "The line type of the product")
  private LineType lineType;

  @ApiModelProperty(notes = "The line status of the product")
  private LineStatus lineStatus;

  @ApiModelProperty(notes = "The payment type  of the product")
  private PaymentType paymentType;

  @ApiModelProperty(notes = "The short number of the product")
  private String shortNumber;

}
