package com.jithin.Ecommerce.utils;

import com.jithin.Ecommerce.models.Category;
import com.jithin.Ecommerce.models.Product;
import com.jithin.Ecommerce.models.ProductImage;
import com.jithin.Ecommerce.models.ProductSize;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
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
            List<ProductImage> image_list = new ArrayList<>();

            for (int j = 0; j < 3; j++) {
                ProductImage pImage = new ProductImage();
                pImage.setImageName("name " + j);
                pImage.setId("img " + j);
                image_list.add(pImage);
            }

            product.setImages(image_list);
            Category category = new Category();
            category.setName("category");
            product.setCategory(category);

            product.setProductCount(5);
            product.setColors(Arrays.asList("RED", "BLUE", "VIOLET"));

            List<ProductSize> sizelist = new ArrayList<>();
            sizelist.add(ProductSize.LARGE);
            sizelist.add(ProductSize.SMALL);
            product.setSize(sizelist);


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

        List<ProductImage> image_list = new ArrayList<>();

        for (int j = 0; j < 3; j++) {
            ProductImage pImage = new ProductImage();
            pImage.setImageName("name " + j);
            pImage.setId("img " + j);
            image_list.add(pImage);
        }

        product.setImages(image_list);
        Category category = new Category();
        category.setName("category");
        product.setCategory(category);

        product.setProductCount(5);
        product.setColors(Arrays.asList("RED", "BLUE", "VIOLET"));

        List<ProductSize> sizelist = new ArrayList<>();
        sizelist.add(ProductSize.LARGE);
        sizelist.add(ProductSize.SMALL);
        product.setSize(sizelist);

        return product;
    }
}
