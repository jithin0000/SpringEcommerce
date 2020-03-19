package com.jithin.Ecommerce.utils;

import com.jithin.Ecommerce.models.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartUtils {
    public static final String CART_ID = "cart_id";


    public static List<Cart> cart_list() {
        List<Cart> cart_list = new ArrayList<>();
        Cart cart = new Cart();
        cart.setId(CART_ID);

        cart_list.add(cart);
        return cart_list;
    }
}
