package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
}
