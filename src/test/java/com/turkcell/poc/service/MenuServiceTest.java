package com.turkcell.poc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.turkcell.poc.converter.MenuMapper;
import com.turkcell.poc.entity.Menu;
import com.turkcell.poc.model.MenuDto;
import com.turkcell.poc.model.MenuCreateDto;
import com.turkcell.poc.repository.MenuRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceTest {

  @InjectMocks
  MenuService menuService;

  @Mock
  MenuMapper menuMapper;

  @Mock
  MenuRepository menuRepository;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getMenuList() {

    List<Menu> menuList = new ArrayList<>();
    Menu menu = new Menu();
    menu.setId("12ads123");
    menu.setName("menu name");
    menu.setMenuDescription("menu description");
    menuList.add(menu);

    List<MenuDto> menuDtoList = new ArrayList<>();
    MenuDto menuDto = new MenuDto();
    menuDto.setId("12ads123");
    menuDto.setName("menu name");
    menuDto.setMenuDescription("menu description");
    menuDtoList.add(menuDto);

    when(menuRepository.findAll()).thenReturn(menuList);
    when(menuMapper.menusToMenuDtos(menuList)).thenReturn(menuDtoList);

    List<MenuDto> response = menuService.getMenuList();

    assertEquals(menuList.size(), response.size());
    assertEquals(menuList.get(0).getId(), response.get(0).getId());
    verify(menuRepository, times(1)).findAll();

  }

  @Test(expected = IllegalArgumentException.class)
  public void saveMenu_NullControl(){
    menuService.insertMenu(null);
  }

  public void saveMenu(){
    MenuCreateDto menuInputDto = new MenuCreateDto();
    menuInputDto.setName("menu name");
    menuInputDto.setMenuDescription("menu description");

    Menu menu = new Menu();
    menu.setName("menu name");
    menu.setMenuDescription("menu description");

    when(menuMapper.menuCreateDtoToMenu(menuInputDto)).thenReturn(menu);
    when(menuRepository.save(menu)).thenReturn(menu);

    MenuDto menuDto = menuService.insertMenu(menuInputDto);

    assertEquals(menuDto.getName(),menuInputDto.getName());
    verify(menuRepository,times(1)).save(menu);

  }

  @Test
  public void deleteMenu() {

    menuService.menuDelete("1");
    verify(menuRepository, times(1)).deleteById(anyString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteMenu_NullControl() {
    menuService.menuDelete(null);

  }
}
