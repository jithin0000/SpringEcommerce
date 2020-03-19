package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.Product;
import com.jithin.Ecommerce.repository.ProductRepository;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends BaseService<ProductRepository, Product> {


    public List<Product> filterProductsByName(String name){

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(name);
        return getRepository().findAllBy(criteria);
    }

}
