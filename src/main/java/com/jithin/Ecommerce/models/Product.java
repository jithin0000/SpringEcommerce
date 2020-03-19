package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Product extends BaseModel {

    @NotNull(message = "name is required field ")
    private String name;
    @NotNull(message = "description is required field ")
    private String description;
    @NotNull(message = "price is required field ")
    private int price = 0;

    private String thumbUrl = "";
    private List<ProductImage> images = new ArrayList<>();


}




















