package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
