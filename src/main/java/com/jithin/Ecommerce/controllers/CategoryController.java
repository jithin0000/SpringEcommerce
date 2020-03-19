package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.dto.DeleteMessage;
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
        Category category = service.getById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable String id) {

        DeleteMessage message = new DeleteMessage();
        message.setMessage(service.deleteById(id));
        return ResponseEntity.ok(message);
    }

    @GetMapping("/name")
    public ResponseEntity<?> filterCategoriesByName(
            @RequestParam(name = "search", defaultValue = "#44") String search
    ) {
        return ResponseEntity.ok(service.categoriesByName(search));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @Valid @RequestBody Category body) {

        Category category = service.getById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        Category update = service.update(category);
        return ResponseEntity.ok(update);

    }
}
