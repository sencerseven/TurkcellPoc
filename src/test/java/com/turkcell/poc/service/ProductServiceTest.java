package com.turkcell.poc.service;


import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.turkcell.poc.converter.ProductMapper;
import com.turkcell.poc.entity.Product;
import com.turkcell.poc.model.ProductDto;
import com.turkcell.poc.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ProductServiceTest {

  @InjectMocks
  ProductService productService;

  @Mock
  ProductRepository productRepository;

  @Mock
  ProductMapper productMapper;

  @Autowired
  ApplicationContext applicationContext;


  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  public void getProductList(){
    List<Product> productList = new ArrayList<>();
    Product product = new Product();
    product.setId("123");
    productList.add(product);

    List<ProductDto> productDtos = new ArrayList<>();
    ProductDto productDto = new ProductDto();
    productDto.setId("123");
    productDtos.add(productDto);


    when(productRepository.findAll()).thenReturn(productList);
    when(productMapper.productsToProductDtos(productList)).thenReturn(productDtos);

    List<ProductDto> response = productService.getProductList();

    assertNotEquals(0, response.size());
    verify(productRepository,times(1)).findAll();
    verify(productMapper,times(1)).productsToProductDtos(productList);


  }


  @SneakyThrows
  @Test
  public void updateProductInfoTest(){
//    List<String> gsmList = new ArrayList<>();
//    gsmList.add("123");
//
//    Executor mock = Mockito.mock(Executor.class);
//
//    when(applicationContext.getBean(anyString())).thenReturn(new ExecutorCompletionService( mock,  new LinkedBlockingQueue<>()));
//
//    productService.updateProductInfo(gsmList);


  }


}
