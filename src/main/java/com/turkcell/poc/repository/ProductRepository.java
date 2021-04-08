package com.turkcell.poc.repository;

import com.turkcell.poc.entity.Product;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
  List<Product> findAllByGsmNumberIn(List<String> gsmNumbers);
}
