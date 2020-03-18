package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
public class UserRole {

    @NotNull(message="name is required field ")
    private String name;
}
