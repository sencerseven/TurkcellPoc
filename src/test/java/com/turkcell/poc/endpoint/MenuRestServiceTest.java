package com.turkcell.poc.endpoint;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.turkcell.poc.controller.MenuController;
import com.turkcell.poc.model.ApiResponse;
import com.turkcell.poc.model.MenuDto;
import com.turkcell.poc.model.MenuCreateDto;
import com.turkcell.poc.service.MenuService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MenuRestServiceTest {

  @InjectMocks
  MenuController menuController;

  @Mock
  MenuService menuService;


  private MockMvc mvc;
  ObjectMapper objectMapper;

  private final String API_URL = "/api/menu";

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(menuController).build();
    objectMapper = JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .build();

  }


  @Test
  public void postTest() throws Exception {

    //when
    when(menuService.insertMenu(any())).thenReturn(null);

    //then
    String requestBodyJson = objectMapper.writeValueAsString(getMenuInputDto());

    MvcResult result = mvc
        .perform(post(API_URL).content(requestBodyJson)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk()).andReturn();

    MockHttpServletResponse response = result.getResponse();
    String responseMessage = response.getContentAsString();
    ApiResponse apiResponse = objectMapper.readValue(responseMessage, ApiResponse.class);

    //expect
    assertEquals(200, response.getStatus());
    assertEquals(HttpStatus.OK, apiResponse.getStatus());
    verify(menuService,times(1)).insertMenu(any());
  }


  @Test
  public void getListTest() throws Exception {
    //when
    when(menuService.getMenuList()).thenReturn(getMenuDtoList());

    //then
    MvcResult result = mvc
        .perform(get(API_URL+"/"))
        .andExpect(status().isOk()).andReturn();

    MockHttpServletResponse response = result.getResponse();
    String responseMessage = response.getContentAsString();
    ApiResponse<List<MenuDto>> apiResponse = objectMapper.readValue(responseMessage, ApiResponse.class);

    //expect
    assertEquals(200, response.getStatus());
    assertEquals(getMenuDtoList().size(),apiResponse.getBody().size());
    verify(menuService,times(1)).getMenuList();
  }


  @Test
  public void deleteTest() throws Exception {
    //when
    when(menuService.menuDelete(anyString())).thenReturn(true);
    //then

    MvcResult result = mvc
        .perform(delete(API_URL + "/1"))
        .andExpect(status().isOk()).andReturn();

    MockHttpServletResponse response = result.getResponse();
    String responseMessage = response.getContentAsString();
    ApiResponse<List<MenuDto>> apiResponse = objectMapper.readValue(responseMessage, ApiResponse.class);

    //expect
    assertEquals(200, response.getStatus());
    verify(menuService,times(1)).menuDelete(anyString());
  }





  public List<MenuCreateDto> getMenuInputDtoList() {
    List<MenuCreateDto> menuList = new ArrayList<>();
    menuList.add(getMenuInputDto());
    return menuList;
  }

  public List<MenuDto> getMenuDtoList() {
    List<MenuDto> menuList = new ArrayList<>();
    menuList.add(getMenuDto());
    return menuList;
  }


  public MenuCreateDto getMenuInputDto() {
    MenuCreateDto menuInputDto = new MenuCreateDto();
    menuInputDto.setName("menu 1");
    menuInputDto.setMenuDescription("menu 1 description");
    return menuInputDto;

  }

  public MenuDto getMenuDto() {
    MenuDto menuDto = new MenuDto();
    menuDto.setName("menu 1");
    menuDto.setMenuDescription("menu 1 description");
    return menuDto;

  }
}
