package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.dto.ProductQueryRequest;
import com.jithin.Ecommerce.models.Product;
import com.jithin.Ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ProductService extends BaseService<ProductRepository, Product> {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> filterProductsByName(String name){

        return getRepository().findByName(name);
    }

    public List<Product> filteredProducts(ProductQueryRequest request) {

        List<Product> data = null;

        if (!request.getCategory().isEmpty()) {

            data = mongoTemplate.find(
                    query(where("category.name").in(request.getCategory())), Product.class
            );
        }

        if (!request.getColors().isEmpty()) {

            data = mongoTemplate.find(
                    query(where("colors").in(request.getColors())), Product.class
            );
        }

        if (!request.getSizes().isEmpty()) {

            data = mongoTemplate.find(
                    query(where("size").in(request.getSizes())), Product.class
            );
        }




        return data;
    }

    public List<Object> findAllDistinctColors(){
        return mongoTemplate.query(Product.class).distinct("colors").all();
    }
    public List<Object> findAllDistinctSizes(){
        return mongoTemplate.query(Product.class).distinct("size").all();
    }

}
