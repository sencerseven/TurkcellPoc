package com.turkcell.poc.entity;

import com.turkcell.poc.constant.LineStatus;
import com.turkcell.poc.constant.LineType;
import com.turkcell.poc.constant.PaymentType;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("product")
public class Product {

  @Id
  private String id;

  @Indexed(unique = true)
  private String gsmNumber;

  private String customerName;

  private LineType lineType;

  private LineStatus lineStatus;

  private PaymentType paymentType;

  private String shortNumber;
}
