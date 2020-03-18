package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.Category;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {

    @Query("{ $text : { $search : ?0 } }")
    List<Category> findByName(String name);

}
