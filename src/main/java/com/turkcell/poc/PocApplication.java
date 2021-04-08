package com.turkcell.poc;

import com.turkcell.poc.constant.LineStatus;
import com.turkcell.poc.constant.LineType;
import com.turkcell.poc.constant.PaymentType;
import com.turkcell.poc.model.ProductCreateDto;
import com.turkcell.poc.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication

public class PocApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(PocApplication.class, args);
  }

  @Autowired
  ApplicationContext applicationContext;

  @Override
  public void run(String... args) throws Exception {
    ProductService productService = (ProductService) applicationContext.getBean("productService");
    List<String> gsmNumberList = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      ProductCreateDto createDto = new ProductCreateDto();
      createDto.setCustomerName("ExampleName_" + i);
      createDto.setGsmNumber(String.valueOf(5325000000L + i));
      createDto.setLineType(LineType.values()[i % LineType.values().length]);
      createDto.setLineStatus(LineStatus.values()[i % LineStatus.values().length]);
      createDto.setPaymentType(PaymentType.values()[i % PaymentType.values().length]);
      createDto.setShortNumber(String.valueOf(1000 + i));

      productService.createProduct(createDto);
      gsmNumberList.add(createDto.getGsmNumber());
    }

    System.out.println("New gsm numbers : "+String.join(",", gsmNumberList));
  }
}
