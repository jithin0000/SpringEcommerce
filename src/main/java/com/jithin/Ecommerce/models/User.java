package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@Document
public class User extends BaseModel implements UserDetails {


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

    private List<UserRole> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set authoraties = new HashSet();
                getRoles().stream()
                .forEach(
                        item ->authoraties.add(new SimpleGrantedAuthority("ROLE_"+ item.getName()))
                );
        return authoraties;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
