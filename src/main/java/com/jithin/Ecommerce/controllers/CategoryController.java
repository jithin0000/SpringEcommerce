package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.exceptions.CategoryNotFoundException;
import com.jithin.Ecommerce.models.Category;
import com.jithin.Ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(service.create(category), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable String id) {
        Category department = service.getById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable String id) {

        String message = service.deleteById(id);
        return ResponseEntity.ok(message);
    }



}