package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.Category;
import com.jithin.Ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService extends BaseService<CategoryRepository, Category> {


    public List<Category> categoriesByName(String name){
        return this.getRepository().findByName(name);

    }

    public Category update(Category category) {

        Category up_category = new Category();
        up_category.setName(category.getName());
        up_category.setUpdated(category.getUpdated());

        return getRepository().save(up_category);
    }
}
