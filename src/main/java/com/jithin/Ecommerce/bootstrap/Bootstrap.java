package com.jithin.Ecommerce.bootstrap;

import com.github.javafaker.Faker;
import com.jithin.Ecommerce.models.Category;
import com.jithin.Ecommerce.models.UserRole;
import com.jithin.Ecommerce.services.BaseService;
import com.jithin.Ecommerce.services.CategoryService;
import com.jithin.Ecommerce.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

//        generateRoles();

//        generateCategories(faker);

    }

    private void generateCategories(Faker faker) {

        for (int i = 0; i < 20; i++) {
            Category category = new Category();
            category.setName(faker.commerce().department());
            categoryService.create(category);
        }

    }

    private void generateRoles() {

        String[] roles = new String[]{"USER", "ADMIN", "MANAGER"};

        for (int i = 0; i < 3; i++) {
            UserRole role = new UserRole();
            role.setName(roles[i]);
            roleService.create(role);
        }

    }
}
