package com.turkcell.poc.model;


import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuUpdateDto implements Serializable {

  private static final long serialVersionUID = 1493248214908377728L;

  @ApiModelProperty(notes = "ID is required to update the menu.")
  private String id;

  private String name;

  private String menuDescription;

}
