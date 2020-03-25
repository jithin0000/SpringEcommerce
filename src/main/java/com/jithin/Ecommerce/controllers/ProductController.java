package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.dto.DeleteMessage;
import com.jithin.Ecommerce.dto.ProductQueryRequest;
import com.jithin.Ecommerce.exceptions.ProductNotFoundException;
import com.jithin.Ecommerce.models.Product;
import com.jithin.Ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.create(product), HttpStatus.CREATED);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterProducts(@Valid @RequestBody ProductQueryRequest request) {
        return new ResponseEntity<>(productService.filteredProducts(request), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        Product department = productService.getById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {

        DeleteMessage message = new DeleteMessage();
        message.setMessage(productService.deleteById(id));

        return ResponseEntity.ok(message);
    }

    @GetMapping("/name")
    public ResponseEntity<?> filterProductByName(
            @RequestParam(name = "search", defaultValue = "#44") String search
    ) {
        return ResponseEntity.ok(productService.filterProductsByName(search));
    }

    @GetMapping("/colors")
    public ResponseEntity<?> findAllColors() {
        return ResponseEntity.ok(productService.findAllDistinctColors());
    }

    @GetMapping("/sizes")
    public ResponseEntity<?> findAllSizes() {
        return ResponseEntity.ok(productService.findAllDistinctSizes());
    }


}


















