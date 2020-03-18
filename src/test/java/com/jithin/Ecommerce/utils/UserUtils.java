package com.jithin.Ecommerce.utils;

import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.models.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {
    public static final String USER_ID = "5e71bcb3c91e21511a6d44a4";
    public static final String USERNAME = "email@gmail.com";
    public static final String PASSWORD = "password";
    public static final String FIRSTNAME = "firstname";
    public static final String GOOGLE_AUTH_ID = "google_auth_id";
    public static final String ENCRYPTED_PASSWORD = "encrypted_password";
    public static final String FACEBOOKTOKEN = "FACEBOOKTOKEN";

    public static User valid_user() {

        User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        user.setPassword(ENCRYPTED_PASSWORD);
        user.setFirstName(FIRSTNAME);
        user.setGoogleToken(GOOGLE_AUTH_ID);
        user.setFacebookToken(FACEBOOKTOKEN);
        List<UserRole> roles = new ArrayList<>();
        UserRole role = new UserRole();
        role.setName("USER");
        roles.add(role);
        user.setRoles(roles);
        return user;
    }
}
