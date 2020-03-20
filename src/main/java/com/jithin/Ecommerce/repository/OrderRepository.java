package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.EOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<EOrder, String> {
}
