package com.turkcell.poc.model;


import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuCreateDto implements Serializable {

  private static final long serialVersionUID = 1493248214908377728L;

  private String name;

  private String menuDescription;

}
