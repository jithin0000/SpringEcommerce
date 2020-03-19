package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository  extends MongoRepository<Product, String> {
}
