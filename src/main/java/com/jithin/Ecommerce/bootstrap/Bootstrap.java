package com.jithin.Ecommerce.bootstrap;

import com.github.javafaker.Faker;
import com.jithin.Ecommerce.models.*;
import com.jithin.Ecommerce.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService imageService;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

//        generateRoles();

//        generateCategories(faker);

//        generateProduct(faker);

    }

    private void generateProduct(Category category, Faker faker) {

            Product product = new Product();
            product.setName(faker.commerce().productName());
            product.setDescription(faker.lorem().sentence(50));
            product.setPrice(faker.number().numberBetween(5688, 315478));

            List<ProductImage> image_list = new ArrayList<>();

            for (int j = 0; j < 3; j++) {
                ProductImage pImage = new ProductImage();
                pImage.setImageName(faker.funnyName().name());

                image_list.add(imageService.create(pImage));
            }

            product.setImages(image_list);
            product.setCategory(category);

            product.setProductCount(5);
            product.setColors(Arrays.asList(faker.color().name(), faker.color().name(),
                    faker.color().name()));

            List<ProductSize> sizelist = new ArrayList<>();
            sizelist.add(ProductSize.LARGE);
            sizelist.add(ProductSize.SMALL);
            sizelist.add(ProductSize.EXTRA_LARGE);
            sizelist.add(ProductSize.MEDIUM);
            product.setSize(Arrays.asList(
                    sizelist.get(faker.number().numberBetween(0,3)),
                    sizelist.get(faker.number().numberBetween(0,3))
            ));

            productService.create(product);
    }

    private void generateCategories(Faker faker) {

        for (int i = 0; i < 20; i++) {
            Category category = new Category();
            category.setName(faker.commerce().department());
            Category createdCategory = categoryService.create(category);

            for (int j = 0; j < 3; j++) {
                generateProduct(createdCategory ,faker);
            }

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
