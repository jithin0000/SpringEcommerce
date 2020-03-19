package com.jithin.Ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartRequestDto {

    private String userId;
    private String productId;

}
