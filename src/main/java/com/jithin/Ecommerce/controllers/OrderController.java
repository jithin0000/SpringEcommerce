package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


}
