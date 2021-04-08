package com.turkcell.poc.converter;

import com.turkcell.poc.entity.Menu;
import com.turkcell.poc.model.MenuDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MenuMapperDecorator implements MenuMapper{

  @Autowired
  MenuMapper delegate;

  @Override
  public List<MenuDto> menusToMenuDtos(List<Menu> menuList) {
    if(menuList.isEmpty())
      return null;

    return menuList.stream().map(delegate::menuToMenuDto)
        .collect(Collectors.toList());

  }
}
