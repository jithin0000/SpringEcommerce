package com.jithin.Ecommerce.dto;

import lombok.Data;

@Data
public class InvalidLoginResponse {

    private final String username;
    private final String password;

    public InvalidLoginResponse() {
        this.username = "invalid username";
        this.password = "invalid password";
    }
}
