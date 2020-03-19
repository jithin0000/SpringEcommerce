package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.Product;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {


    List<Product> findAllBy(TextCriteria criteria);
}
