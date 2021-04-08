package com.turkcell.poc.controller;

import com.turkcell.poc.model.ApiResponse;
import com.turkcell.poc.model.ProductCreateDto;
import com.turkcell.poc.model.ProductDto;
import com.turkcell.poc.model.ProductUpdateDto;
import com.turkcell.poc.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Product.")
public class ProductController {

  @Autowired
  private ProductService productService;

  /**
   * Used to pull the Product List.
   *
   * @param
   * @since 2021
   */
  @RequestMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.GET
  )
  @ApiOperation("Returns list of all Product in the system.")
  public ResponseEntity<ApiResponse<List<ProductDto>>> getProductList() {
    List<ProductDto> productList = productService.getProductList();
    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, productList, "Success!"),
        HttpStatus.OK);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ApiOperation("This method is used to create new product in the system.")
  public ResponseEntity<ApiResponse<ProductDto>> createProduct(
      @RequestBody ProductCreateDto productInputDto
  ) {
    ProductDto productDto = productService.createProduct(productInputDto);
    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, productDto, "Success!"),
        HttpStatus.OK);
  }

  @RequestMapping(
      method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ApiOperation("This method is used to update current product in the system.")
  public ResponseEntity<ApiResponse<List<ProductDto>>> updateProducts(
      @RequestBody List<ProductUpdateDto> productUpdtInputDtos
  ) throws InterruptedException, ExecutionException {
    List<ProductDto> updateResult = productService.updateProduct(productUpdtInputDtos);
    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, updateResult, "Success!"),
        HttpStatus.OK);
  }

  /**
   * The short number information of the gsm numbers sent in a list using the
   *  multithread structure is changed.
   *
   * @param gsmNumbers
   * @return
   * @throws InterruptedException
   * @throws ExecutionException
   */
  @RequestMapping(
      value = "/product-info",
      method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ApiOperation("Used to batch update the short number field by gsm numbers")
  public ResponseEntity<ApiResponse<List<ProductDto>>> updateProductInfo(
      @RequestBody List<String> gsmNumbers
  ) throws InterruptedException, ExecutionException {
    List<ProductDto> updateResult = productService.updateProductInfo(gsmNumbers);
    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, updateResult, "Success!"),
        HttpStatus.OK);
  }

  @RequestMapping(
      value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.DELETE
  )
  @ApiOperation("This method is used to delete menu by id in the system.")
  public ResponseEntity<ApiResponse<Boolean>> deleteProduct(
      @PathVariable("id") String id
  ) {
    Boolean result = productService.deleteProduct(id);
    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, result, "Success!"),
        HttpStatus.OK);
  }


}
