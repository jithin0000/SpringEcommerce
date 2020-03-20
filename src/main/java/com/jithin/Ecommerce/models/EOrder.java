package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
public class EOrder extends BaseModel {

    @NotNull(message="user is required field ")
    private User user;
    @NotNull(message="cart is required field ")
    private Cart cart;

    private int orderTotal = 0;
    private OrderStatus status = OrderStatus.PLACED;

}
