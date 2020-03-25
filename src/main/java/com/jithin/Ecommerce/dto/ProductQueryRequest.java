package com.jithin.Ecommerce.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductQueryRequest {

    private List<String> category = new ArrayList<>();
    private List<String> colors = new ArrayList<>();
    private List<String> sizes = new ArrayList<>();

}
