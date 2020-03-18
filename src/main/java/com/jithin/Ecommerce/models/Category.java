package com.jithin.Ecommerce.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
@NoArgsConstructor
public class Category extends BaseModel {


    @NotNull(message="name is required field ")
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
