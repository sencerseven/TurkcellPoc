package com.turkcell.poc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String userName;
  private String password;
  private int active;

  private List<RoleDto> roles = new ArrayList<>();

  public UserDto(UserDto userDto) {
    this.id = userDto.getId();
    this.userName = userDto.getUserName();
    this.password = userDto.getPassword();
    this.active = userDto.getActive();
    this.roles = userDto.getRoles();
  }


}
