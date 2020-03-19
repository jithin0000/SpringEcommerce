package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document
public class ProductImage extends BaseModel {

    private String imageName;


}
