package com.jithin.Ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException(String roleName) {
        super("no role with this name " + roleName);
    }
}
