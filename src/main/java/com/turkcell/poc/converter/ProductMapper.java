package com.turkcell.poc.converter;

import com.turkcell.poc.entity.Product;
import com.turkcell.poc.model.ProductDto;
import com.turkcell.poc.model.ProductCreateDto;
import com.turkcell.poc.model.ProductUpdateDto;
import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

  public ProductDto productToProductDto(Product product);

  public List<ProductDto> productsToProductDtos(List<Product> productList);

  public Product productCreateDtoToProduct(ProductCreateDto productCreateDto);

  public List<Product> productCreateDtosToProductDtos(List<ProductCreateDto> productCreateDtoList);

  public Product productUpdateDtoProduct(ProductUpdateDto productUpdateDto);

  public List<Product> productUpdateDtosToProductDtos(List<ProductUpdateDto> productUpdateDtos);

}
