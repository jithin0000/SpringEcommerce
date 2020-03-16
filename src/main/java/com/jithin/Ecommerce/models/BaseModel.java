package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Data
public class BaseModel {

    @Id
    private String id;


}
