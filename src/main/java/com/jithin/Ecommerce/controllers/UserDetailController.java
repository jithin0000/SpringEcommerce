package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.exceptions.UserNotFoundException;
import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.services.CustomUserDetailServices;
import com.jithin.Ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/detail")
public class UserDetailController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailServices userDetailServices;


    @GetMapping("/me")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal Principal principal) {

        String username = principal.getName();
        User user = (User) userDetailServices.loadUserByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("me");
        }

        return ResponseEntity.ok(user);
    }
}
