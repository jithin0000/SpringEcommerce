package com.jithin.Ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String id) {
        super("no cart with this id " + id);
    }
}
