package com.jithin.Ecommerce.utils;

import com.jithin.Ecommerce.models.Cart;
import com.jithin.Ecommerce.models.EOrder;

import java.util.ArrayList;
import java.util.List;

import static com.jithin.Ecommerce.utils.UserUtils.valid_user;

public class OrderUtils {
    public static final String ORDER_ID = "order_id";

    public static EOrder valid_order() {

        EOrder order = new EOrder();
        order.setId(ORDER_ID);
        order.setCart(valid_cart());
        order.setUser(valid_user());
        return order;
    }

    public static List<EOrder> order_list() {
        List<EOrder> orderList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            EOrder order = new EOrder();
            order.setId(ORDER_ID+i);
            order.setCart(valid_cart());
            order.setUser(valid_user());
            orderList.add(order);
        }

        return orderList;
    }

    public static Cart valid_cart() {
        return new Cart();
    }
}
