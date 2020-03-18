package com.jithin.Ecommerce.dto;

import com.jithin.Ecommerce.models.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class RegisterRequestDto {


    @NotNull(message = "username is required field ")
    @Email(message = "should enter valid email")
    private String username;
    @NotNull(message="password is required field ")
    private String password;

    @NotNull(message="firstName is required field ")
    private String firstName;

    private String googleToken;
    private String facebookToken;

    private String profilePicture;
    private String phoneNumber;

    private List<String> roles ;
}
