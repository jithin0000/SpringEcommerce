package com.jithin.Ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class BaseService<T extends MongoRepository<U, String>, U> {

    @Autowired
    private T repository;

    public T getRepository() {
        return repository;
    }
}
