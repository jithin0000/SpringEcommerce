package com.jithin.Ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SocialLoginRequest {

    @NotNull(message="provider is required field ")
    private String provider;

    private String googleAuthToken;
    private FacebookRequest facebookRequest;

}
