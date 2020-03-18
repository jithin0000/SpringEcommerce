package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
public class Category extends BaseModel {


    @NotNull(message="name is required field ")
    @TextIndexed
    private String name;

}
