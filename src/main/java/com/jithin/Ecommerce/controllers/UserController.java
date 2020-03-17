package com.jithin.Ecommerce.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.jithin.Ecommerce.dto.JwtResponse;
import com.jithin.Ecommerce.dto.LoginRequestDto;
import com.jithin.Ecommerce.dto.SocialLoginRequest;
import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.security.JwtTokenProvider;
import com.jithin.Ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        User registerUser = userService.registerUser(user);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto user) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtTokenProvider.generateToken(authentication);

        JwtResponse response = new JwtResponse("Bearer " + jwtToken);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/social")
    public ResponseEntity<?> socialLogin() {

        User user = new User();
        JwtResponse response = null;
        HttpTransport transport = new NetHttpTransport();

        JsonFactory jsonFactory = new JacksonFactory();
        String cilentId ="735347369403-hu7c6e1kdbs5ggct9raa7e42oi4oo8vi.apps.googleusercontent.com";
        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                        .setAudience(Collections.singletonList(cilentId))
                        .build();

        String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImE1NDFkNmVmMDIyZDc3YTIzMThmN2RkNjU3ZjI3NzkzMjAzYmVkNGEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNzM1MzQ3MzY5NDAzLWh1N2M2ZTFrZGJzNWdnY3Q5cmFhN2U0Mm9pNG9vOHZpLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNzM1MzQ3MzY5NDAzLWh1N2M2ZTFrZGJzNWdnY3Q5cmFhN2U0Mm9pNG9vOHZpLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTEwNjI2NDAzNTc0ODI4NDkzMjc2IiwiZW1haWwiOiJtYmppdGhpbmJhYnVAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJKSXJVMjNhRy16WENUZ1dWOWN6NWtnIiwibmFtZSI6ImppdGhpbmJhYnUgbWIiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FPaDE0R2lKcUR6VDZweXBBMjd5MW43bTdHTG9yZU5BTGc4MjZMMkRzTkxoT2c9czk2LWMiLCJnaXZlbl9uYW1lIjoiaml0aGluYmFidSIsImZhbWlseV9uYW1lIjoibWIiLCJsb2NhbGUiOiJlbiIsImlhdCI6MTU4NDQyNTI5MCwiZXhwIjoxNTg0NDI4ODkwLCJqdGkiOiIzMmViM2RhZWE1MDIzMmJjZTljOGVlY2NkOWRiYTViMzhiMTMxZWY3In0.TQF7J-SMD3PM513cdsICh31z2zz1HCy3CdGDxVK6pZOzYNAAD_YvPcbpSyZH3rPg6aqT4VB-wfgbuGFHNnXBQxhXEgxqeKdlzwIX8ijlf-oFM0wbeFuW9H1pPOcfolv46jrdTCPg8YwsUGDpcA-DBEm7o4qc1wPSC_fmTUT9r7UET4a8ir_ZD2jvbfG_fdmgi6MUJPHD1TL-Y5pNEea6oImvhUMB7HHAfzBAvbt1b-fP0W123qU9FN7D3RgKbo3bMAqm_8tDeloDoZ4PaJeZzZqKGyPkg7LEdxiyTE3Xcd-hjXAS1w1vlap5BdvtNguI-Bm-Dyy1yvruAMytW8QEdw";

        GoogleIdToken.Payload payload = null;
        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);

            if (googleIdToken != null) {
                 payload = googleIdToken.getPayload();

                String googleAuthToken = payload.getSubject();
               User  dbUser = userService.getUserByGoogleAuth(googleAuthToken);

                if (dbUser == null) {
                    String name = (String) payload.get("name");
                    String email =  payload.getEmail();
                    String profilePicture = (String) payload.get("picture");

                    user.setGoogleToken(googleAuthToken);
                    user.setUsername(email);
                    user.setFirstName(name);
                    user.setPassword(googleAuthToken);

                    user.setProfilePicture(profilePicture);
                    User registered = userService.registerUser(user);

                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    registered.getUsername(), googleAuthToken
                            )
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    String jwtToken = jwtTokenProvider.generateToken(authentication);

                    response = new JwtResponse("Bearer " + jwtToken);

                }else {

                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    dbUser.getUsername(), dbUser.getGoogleToken()
                            )
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    String jwtToken = jwtTokenProvider.generateToken(authentication);

                    response = new JwtResponse("Bearer " + jwtToken);
                }

            }

        } catch (GeneralSecurityException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return ResponseEntity.ok(response);
    }
}












