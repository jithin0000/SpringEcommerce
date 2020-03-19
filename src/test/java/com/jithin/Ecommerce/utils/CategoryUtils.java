package com.jithin.Ecommerce.utils;

import com.jithin.Ecommerce.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CategoryUtils {
    public static final String UPDATED_CATEGORY = "UPDATED_CATEGORY";

    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "category IName";
    public static List<Category> category_list() {

        List<Category> c_list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Category c = new Category();
            c.setName(CATEGORY_NAME +" "+ i);
            c.setId(CATEGORY_ID +i);
            c_list.add(c);
        }
        return c_list;
    }

    public static Category valid_category(){
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        category.setId(CATEGORY_ID);
        return category;
    }
    public static List<Category> filtered_category_list() {

        return category_list().stream().filter(item -> item.getName().contains(CATEGORY_NAME))
                .collect(Collectors.toList());
    }

    public static Category updated_category() {
        Category u_category = new Category();
        u_category.setId(CATEGORY_ID);
        u_category.setName(UPDATED_CATEGORY);
        return u_category;
    }


}
