package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.dto.CartRequestDto;
import com.jithin.Ecommerce.exceptions.CartNotFoundException;
import com.jithin.Ecommerce.exceptions.ProductNotFoundException;
import com.jithin.Ecommerce.models.Cart;
import com.jithin.Ecommerce.models.Product;
import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.services.CartService;
import com.jithin.Ecommerce.services.CustomUserDetailServices;
import com.jithin.Ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService service;
    @Autowired
    private CustomUserDetailServices userService;
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCart(@PathVariable String id) {
        Cart department = service.getById(id).orElseThrow(() -> new CartNotFoundException(id));
        return ResponseEntity.ok(department);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequestDto body) {

        Cart cart = new Cart();
        User user = null;
        if (body.getUserId() != null) {
            user = userService.loadByUserId(body.getUserId());
            cart.setUser(user);


        }

        Product product = null;
        if (body.getProductId() != null) {
            product = productService.getById(body.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(body.getProductId()));

            if (cart.getProducts().isEmpty()) {

                cart.getProducts().add(product);

            } else {

                if (cart.getProducts().contains(product)) {
                    cart.getProducts().remove(product);
                }else {
                    cart.getProducts().add(product);
                }
            }


        }

        return new ResponseEntity<>(service.create(cart), HttpStatus.CREATED);

    }
}



















