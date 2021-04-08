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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.turkcell.poc.constant.LineStatus;
import com.turkcell.poc.constant.LineType;
import com.turkcell.poc.constant.PaymentType;
import com.turkcell.poc.controller.ProductController;
import com.turkcell.poc.model.ApiResponse;
import com.turkcell.poc.model.MenuDto;
import com.turkcell.poc.model.ProductDto;
import com.turkcell.poc.model.ProductCreateDto;
import com.turkcell.poc.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

public class ProductRestServiceTest {

  @InjectMocks
  ProductController productController;

  @Mock
  ProductService productService;


  private MockMvc mvc;
  ObjectMapper objectMapper;

  private final String API_URL = "/api/product";

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(productController).build();
    objectMapper = JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .build();

  }


  @Test
  public void putTest() throws Exception {

    //when
    when(productService.updateProduct(any())).thenReturn(null);

    //then
    String requestBodyJson = objectMapper.writeValueAsString(getProductInputDtoList());

    MvcResult result = mvc
        .perform(put(API_URL+"/").content(requestBodyJson)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk()).andReturn();

    MockHttpServletResponse response = result.getResponse();
    String responseMessage = response.getContentAsString();
    ApiResponse apiResponse = objectMapper.readValue(responseMessage, ApiResponse.class);

    //expect
    assertEquals(200, response.getStatus());
    assertEquals(HttpStatus.OK, apiResponse.getStatus());
    verify(productService,times(1)).updateProduct(any());
  }


  @Test
  public void getListTest() throws Exception {
    //when
    when(productService.getProductList()).thenReturn(getProductDtoList());

    //then
    MvcResult result = mvc
        .perform(get(API_URL+"/"))
        .andExpect(status().isOk()).andReturn();

    MockHttpServletResponse response = result.getResponse();
    String responseMessage = response.getContentAsString();
    ApiResponse<List> apiResponse = objectMapper.readValue(responseMessage, ApiResponse.class);

    //expect
    assertEquals(200, response.getStatus());
    assertEquals(getProductDtoList().size(),apiResponse.getBody().size());
    verify(productService,times(1)).getProductList();
  }


  @Test
  public void deleteTest() throws Exception {
    //when
    when(productService.deleteProduct(anyString())).thenReturn(true);
    //then

    MvcResult result = mvc
        .perform(delete(API_URL + "/1"))
        .andExpect(status().isOk()).andReturn();

    MockHttpServletResponse response = result.getResponse();
    String responseMessage = response.getContentAsString();
    ApiResponse<List<MenuDto>> apiResponse = objectMapper.readValue(responseMessage, ApiResponse.class);

    //expect
    assertEquals(200, response.getStatus());
    verify(productService,times(1)).deleteProduct(anyString());
  }





  public List<ProductCreateDto> getProductInputDtoList() {
    List<ProductCreateDto> productInputDtos = new ArrayList<>();
    productInputDtos.add(getProductInputDto());
    return productInputDtos;
  }

  public List<ProductDto> getProductDtoList() {
    List<ProductDto> productDtos = new ArrayList<>();
    productDtos.add(getProductDto());
    return productDtos;
  }


  public ProductCreateDto getProductInputDto() {
    ProductCreateDto productInputDto = new ProductCreateDto();

    productInputDto.setCustomerName("sencer");
    productInputDto.setGsmNumber("23411234");
    productInputDto.setLineStatus(LineStatus.ACTIVE);
    productInputDto.setPaymentType(PaymentType.CREDIT_CARD);
    productInputDto.setLineType(LineType.GSM850);

    return productInputDto;

  }

  public ProductDto getProductDto() {
    ProductDto productDto = new ProductDto();

    productDto.setCustomerName("sencer");
    productDto.setGsmNumber("23411234");
    productDto.setLineStatus(LineStatus.ACTIVE);
    productDto.setPaymentType(PaymentType.CREDIT_CARD);
    productDto.setLineType(LineType.GSM850);
    productDto.setShortNumber(String.valueOf(new Random().nextInt(9999)));

    return productDto;
  }
}
