package com.turkcell.poc.constant;

import lombok.Getter;

@Getter
public enum RoleType {

  ADMIN(1);

  private int role;

  RoleType(int role) {
    this.role = role;
  }
}
