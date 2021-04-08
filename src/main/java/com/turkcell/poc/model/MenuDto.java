package com.turkcell.poc.model;


import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuDto implements Serializable {

  private static final long serialVersionUID = 1493248214908377728L;

  @ApiModelProperty(notes = "The auto-generated ID of the menu")
  private String id;

  @ApiModelProperty(notes = "The name of the menu")
  private String name;

  @ApiModelProperty(notes = "The menu description of the product")
  private String menuDescription;

}
