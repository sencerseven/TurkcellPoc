package com.turkcell.poc.converter;

import com.turkcell.poc.entity.Product;
import com.turkcell.poc.model.ProductCreateDto;
import com.turkcell.poc.model.ProductDto;
import com.turkcell.poc.model.ProductUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProductMapperDecorator implements ProductMapper {

  @Autowired
  ProductMapper delegate;

  @Override
  public List<ProductDto> productsToProductDtos(List<Product> productList) {
    if (productList == null || productList.isEmpty()) {
      return null;
    }

    return productList.stream().map(delegate::productToProductDto).collect(Collectors.toList());
  }

  @Override
  public List<Product> productCreateDtosToProductDtos(List<ProductCreateDto> productCreateDtoList) {
    if (productCreateDtoList == null || productCreateDtoList.isEmpty()) {
      return null;
    }

    return productCreateDtoList.stream().map(delegate::productCreateDtoToProduct)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> productUpdateDtosToProductDtos(
      List<ProductUpdateDto> productUpdateDtos) {
    if (productUpdateDtos == null || productUpdateDtos.isEmpty()) {
      return null;
    }
    return productUpdateDtos.stream().map(delegate::productUpdateDtoProduct)
        .collect(Collectors.toList());

  }

}
