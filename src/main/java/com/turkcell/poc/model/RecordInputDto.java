package com.turkcell.poc.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecordInputDto implements Serializable {

  private Date createDate;

  private String uri;

  private String body;

  private String owner;

  private String requestType;

}
