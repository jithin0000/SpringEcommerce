package com.jithin.Ecommerce.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;

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

    @NotNull(message="google_auth_id is required field ")
    private String googleToken;

    private String profilePicture;
    private String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
