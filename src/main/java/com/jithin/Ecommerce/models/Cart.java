package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Cart extends BaseModel {

    private User user;
    private List<Product> products = new ArrayList<>();

}
