package com.jithin.Ecommerce.repository;

import com.jithin.Ecommerce.models.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<UserRole, String> {
    Optional<UserRole> findByName(String roleName);
}
