package com.jithin.Ecommerce.utils;

import com.jithin.Ecommerce.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryUtils {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryIName";
    public static List<Category> category_list() {

        List<Category> c_list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Category c = new Category(CATEGORY_NAME + i);
            c.setId(CATEGORY_ID +i);
            c_list.add(c);
        }
        return c_list;
    }

    public static Category valid_category(){
        Category category = new Category(CATEGORY_NAME);
        category.setId(CATEGORY_ID);
        return category;
    }

}