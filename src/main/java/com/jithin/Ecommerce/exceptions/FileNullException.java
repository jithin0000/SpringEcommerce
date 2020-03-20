package com.jithin.Ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileNullException extends RuntimeException {

    public FileNullException(String message) {
        super(message);
    }
}
