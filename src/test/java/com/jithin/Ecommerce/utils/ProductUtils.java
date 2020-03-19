package com.jithin.Ecommerce.utils;

import com.jithin.Ecommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUtils {
    public static final String PRODUCTID = "productid";
    public static final String PRODUCTNAME = "productname";
    public static final String DESCRIPTION = "description";
    public static List<Product> productList() {
        List<Product> pdt_list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Product product = new Product();
            product.setId(PRODUCTID+i);
            product.setName(PRODUCTNAME + i);
            product.setDescription(DESCRIPTION + " " + i);
            product.setPrice(45);
            pdt_list.add(product);
        }



        return pdt_list;
    }

    public static Product valid_product() {
        Product product = new Product();
        product.setId(PRODUCTID);
        product.setName(PRODUCTNAME);
        product.setDescription(DESCRIPTION + " ");
        product.setPrice(45);
        return product;
    }
}
