package com.turkcell.poc.service;

import com.turkcell.poc.converter.MenuMapper;
import com.turkcell.poc.entity.Menu;
import com.turkcell.poc.model.MenuDto;
import com.turkcell.poc.model.MenuCreateDto;
import com.turkcell.poc.model.MenuUpdateDto;
import com.turkcell.poc.repository.MenuRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class MenuService {

  private final Logger logger = LoggerFactory.getLogger(MenuService.class);

  @Autowired
  MenuRepository menuRepository;

  @Autowired
  MenuMapper menuMapper;

  /**
   *
   * Used to pull the menu list
   *
   * @return
   * @throws EntityNotFoundException
   */
  @Cacheable(value = "Menu", key = "'menu_list'")
  public List<MenuDto> getMenuList() throws EntityNotFoundException {

    List<Menu> menuList = menuRepository.findAll();

    if (menuList.isEmpty()) {
      throw new EntityNotFoundException("No find any menu items");
    }

    List<MenuDto> menuDtos = menuMapper.menusToMenuDtos(menuList);
    this.logger.info("Get menu request returning {} data", menuDtos.size());
    return menuDtos;
  }

  /**
   * It is the function used to add a new menu item.
   *
   * @param menuInputDto
   * @return
   */
  @CacheEvict(value = "Menu", key = "'menu_list'", allEntries = true)
  public MenuDto insertMenu(MenuCreateDto menuInputDto) {

    if (menuInputDto == null) {
      throw new IllegalArgumentException("input cannot be null");
    }

    Menu menu = menuMapper.menuCreateDtoToMenu(menuInputDto);
    menu = menuRepository.save(menu);
    this.logger.info("New Menu Created {}", menu);
    return this.menuMapper.menuToMenuDto(menu);
  }

  /**
   *  It is the function used to update the menu content.
   *
   * @param menuUpdateInputDto
   * @return
   */
  @CacheEvict(value = "Menu", key = "'menu_list'", allEntries = true)
  public MenuDto updateMenu(MenuUpdateDto menuUpdateInputDto) {

    if (menuUpdateInputDto == null) {
      throw new IllegalArgumentException("input cannot be null");
    }

    Menu menu = menuMapper.menuUpdateDtoToMenu(menuUpdateInputDto);
    menu = menuRepository.save(menu);
    this.logger.info("Menu Updated {}", menu);
    return this.menuMapper.menuToMenuDto(menu);
  }

  /**
   *  Performs deletion according to id in menu content.
   *
   * @param id
   * @return
   */
  @CacheEvict(value = "Menu", key = "'menu_list'", allEntries = true)
  public boolean menuDelete(String id) {
    if (StringUtils.isEmpty(id)) {
      throw new IllegalArgumentException("id cannot be null");
    }
    menuRepository.deleteById(id);
    this.logger.info("Menu Deleted With Id : {}", id);
    return true;
  }

}
