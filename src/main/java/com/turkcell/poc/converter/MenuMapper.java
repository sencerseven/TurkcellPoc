package com.turkcell.poc.converter;

import com.turkcell.poc.entity.Menu;
import com.turkcell.poc.model.MenuDto;
import com.turkcell.poc.model.MenuCreateDto;
import com.turkcell.poc.model.MenuUpdateDto;
import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(MenuMapperDecorator.class)
public interface MenuMapper {

  public MenuDto menuToMenuDto(Menu menu);

  public List<MenuDto> menusToMenuDtos(List<Menu> menuList);

  public Menu menuCreateDtoToMenu(MenuCreateDto menuCreateDto);

  public Menu menuUpdateDtoToMenu(MenuUpdateDto menuUpdateDto);

}
