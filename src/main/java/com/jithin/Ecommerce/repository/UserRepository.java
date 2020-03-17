package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    User getById(String id);

    User findByGoogleToken(String token);
}

