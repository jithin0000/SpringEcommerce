package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends MongoRepository<U, String>, U> {

    @Autowired
    private T repository;

    public T getRepository() {
        return repository;
    }

    public Optional<U> getById(String id) {
        return repository.findById(id);
    }

    public List<U> getAll() {
        return repository.findAll();
    }

    public U create(U body) {
        return repository.save(body);
    }

    public String deleteById(String id) {
        repository.deleteById(id);
        return "item deleted with id "+id;
    }
}
