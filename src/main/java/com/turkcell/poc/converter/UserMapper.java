package com.turkcell.poc.converter;

import com.turkcell.poc.entity.Users;
import com.turkcell.poc.model.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

  public UserDto userToUserDto(Users entity);
}
