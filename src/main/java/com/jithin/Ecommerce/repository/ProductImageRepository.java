package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.ProductImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductImageRepository extends MongoRepository<ProductImage, String> {
}
