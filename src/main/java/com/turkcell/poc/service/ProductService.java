package com.turkcell.poc.service;

import com.turkcell.poc.converter.ProductMapper;
import com.turkcell.poc.entity.Product;
import com.turkcell.poc.model.ProductCreateDto;
import com.turkcell.poc.model.ProductDto;
import com.turkcell.poc.model.ProductUpdateDto;
import com.turkcell.poc.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductService {

  private final Logger logger = LoggerFactory.getLogger(ProductService.class);

  private final Random random = new Random();

  @Value("${taskExecutor.queueCapacity}")
  private Integer queueCapacity;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductMapper productMapper;

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Used to pull the Product List.
   *
   * @param
   * @author Burak Akyıldız
   * @author Sencer Seven
   * @since 2021
   */
  public List<ProductDto> getProductList() {
    List<Product> productList = productRepository.findAll();
    this.logger.info("Get product list returns {} product.", productList.size());
    return productMapper.productsToProductDtos(productList);
  }

  /**
   * Used for new product.
   *
   * @param productInputDto
   * @return
   * @author Burak Akyıldız
   * @author Sencer Seven since 2021
   */
  public ProductDto createProduct(ProductCreateDto productInputDto) {
    if (productInputDto == null ||
        productInputDto.getGsmNumber() == null ||
        productInputDto.getGsmNumber().isEmpty()) {
      throw new IllegalArgumentException("Product create info is empty");
    }
    Product product = productMapper.productCreateDtoToProduct(productInputDto);
    Product insert = productRepository.insert(product);

    return productMapper.productToProductDto(insert);
  }

  /**
   * The information of the product object in a list is updated using the multithread structure.
   *
   * @param productUpdateDtos
   * @return
   * @throws InterruptedException
   * @throws ExecutionException
   * @author Burak Akyıldız
   * @author Sencer Seven
   * @since 2021
   */
  public List<ProductDto> updateProduct(List<ProductUpdateDto> productUpdateDtos)
      throws InterruptedException, ExecutionException {

    if (productUpdateDtos == null || productUpdateDtos.size() == 0) {
      return new ArrayList<>();
    }

    if (productUpdateDtos.size() > this.queueCapacity) {
      throw new IllegalStateException(
          "Too many product to update !"
              + " Product count to update is must be lower then queue capacity !");
    }

    this.logger.info("Update product list data. Received {} product data. Starting to update.",
        productUpdateDtos.size());

    ExecutorCompletionService<Product> executorCompletionService =
        (ExecutorCompletionService<Product>) applicationContext
            .getBean("executorCompletionService");

    List<Product> products = productMapper.productUpdateDtosToProductDtos(productUpdateDtos);

    products.forEach(p -> executorCompletionService.submit(() -> {
      p.setShortNumber(String.valueOf(Math.abs(random.nextInt() % 10000)));
      return productRepository.save(p);
    }));

    List<ProductDto> result = this.waitUpdateResult(products, executorCompletionService);

    this.logger.info("Product list update is done.");
    return result;
  }

  /**
   * The short number information of the gsm numbers sent in a list using the
   * multithread structure is changed.
   *
   * @param gsmList
   * @return
   * @throws InterruptedException
   * @throws ExecutionException
   * @author Burak Akyıldız
   * @author Sencer Seven
   * @since 2021
   */
  public List<ProductDto> updateProductInfo(List<String> gsmList)
      throws InterruptedException, ExecutionException {

    if (gsmList == null || gsmList.size() == 0) {
      return new ArrayList<>();
    }

    if (gsmList.size() > this.queueCapacity) {
      throw new IllegalStateException(
          "Too many gsm to update !"
              + " Gsm count to update is must be lower then queue capacity !");
    }

    gsmList = gsmList.stream().distinct().collect(Collectors.toList());

    this.logger.info(
        "Update product info list data. Received {} gsm number. Starting to update short number.",
        gsmList.size());

    ExecutorCompletionService<Product> executorCompletionService =
        (ExecutorCompletionService<Product>) applicationContext
            .getBean("executorCompletionService");

    List<Product> products = this.productRepository.findAllByGsmNumberIn(gsmList);

    if (products.size() != gsmList.size()) {
      throw new IllegalArgumentException("Unknown Gsm Number ! There can be more than one same gsm number.");
    }

    products.forEach(p -> executorCompletionService.submit(() -> {
      p.setShortNumber(String.valueOf(Math.abs(random.nextInt() % 10000)));
      return productRepository.save(p);
    }));

    List<ProductDto> result = this.waitUpdateResult(products, executorCompletionService);

    this.logger.info("Product list short number update is done.");
    return result;
  }

  private List<ProductDto> waitUpdateResult(
      List<Product> products, ExecutorCompletionService<Product> executorCompletionService
  ) throws InterruptedException, ExecutionException {
    List<ProductDto> result = new ArrayList<>();
    for (int i = 0; i < products.size(); i++) {
      Future<Product> jobResult = executorCompletionService.take();
      if (jobResult.isDone() && jobResult.isCancelled()) {
        throw new IllegalStateException("Update product is cancelled");
      }
      ProductDto productDto = this.productMapper.productToProductDto(jobResult.get());
      result.add(productDto);
      this.logger.debug("Product is updated. Product : {}", productDto);
    }
    return result;
  }

  /**
   * It is the function used to delete the product by id.
   *
   * @param id
   * @return
   */
  public boolean deleteProduct(String id) {

    if (StringUtils.isEmpty(id)) {
      throw new IllegalArgumentException("Id cannot be null");
    }

    productRepository.findById(id)
        .ifPresentOrElse(product -> productRepository.deleteById(id),
            () -> {
              throw new EntityNotFoundException("Product not found !");
            });

    this.logger.info("Product is deleted. Product id : {}", id);
    return true;
  }

}
