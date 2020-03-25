package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.Product;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collections;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {


    @Query("{ $text : { $search : ?0 } }")
    List<Product> findByName(String name);

    List<Product> findByCategory_NameIn(List<String> categoryNames);

}
