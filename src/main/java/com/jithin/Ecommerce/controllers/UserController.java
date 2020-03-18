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
import com.jithin.Ecommerce.dto.RegisterRequestDto;
import com.jithin.Ecommerce.dto.SocialLoginRequest;
import com.jithin.Ecommerce.exceptions.UserNotFoundException;
import com.jithin.Ecommerce.exceptions.UserRoleNotFoundException;
import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.models.UserRole;
import com.jithin.Ecommerce.security.JwtTokenProvider;
import com.jithin.Ecommerce.services.RoleService;
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

import javax.management.relation.RoleNotFoundException;
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
    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequestDto dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());

        if (!dto.getRoles().isEmpty()) {
            for (int i = 0; i < dto.getRoles().size(); i++) {

                String roleName = dto.getRoles().get(i);
                UserRole role = roleService.getByName(roleName)
                        .orElseThrow(() -> new UserRoleNotFoundException(roleName));

                user.getRoles().add(role);
            }
        }else{
            UserRole role = roleService.getByName("USER")
                    .orElseThrow(() -> new UserRoleNotFoundException("USER"));
            user.getRoles().add(role);

        }

        User registerUser = userService.registerUser(user);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto user) {


        String jwtToken = generateTokenUsingUsernameAndPassword(user.getPassword(), user.getUsername());

        JwtResponse response = new JwtResponse("Bearer " + jwtToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/social")
    public ResponseEntity<?> socialLogin(@Valid @RequestBody SocialLoginRequest request) {

        // TODO: 17/03/20 need to refract this
        User user = new User();
        JwtResponse response = new JwtResponse();



        if (request.getProvider().equals("GOOGLE")){

            HttpTransport transport = new NetHttpTransport();

            JsonFactory jsonFactory = new JacksonFactory();
            String cilentId ="735347369403-hu7c6e1kdbs5ggct9raa7e42oi4oo8vi.apps.googleusercontent.com";
            GoogleIdTokenVerifier verifier =
                    new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                            .setAudience(Collections.singletonList(cilentId))
                            .build();

            String idToken = request.getGoogleAuthToken();


            try {
                GoogleIdToken googleIdToken = verifier.verify(idToken);

                if (googleIdToken != null) {
                    GoogleIdToken.Payload payload = googleIdToken.getPayload();

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
                        UserRole role = roleService.getByName("USER")
                                .orElseThrow(() -> new UserRoleNotFoundException("USER"));
                        user.getRoles().add(role);

                        user.setProfilePicture(profilePicture);
                        User registered = userService.registerUser(user);

                        String jwtToken = generateTokenUsingUsernameAndPassword(googleAuthToken,
                                registered.getUsername());

                        response = new JwtResponse("Bearer " + jwtToken);

                    }else {

                        String jwtToken = generateTokenUsingUsernameAndPassword(dbUser.getGoogleToken(),
                                dbUser.getUsername());

                        response = new JwtResponse("Bearer " + jwtToken);
                    }

                }

            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());

            }
        } else if (request.getProvider().equals("FACEBOOK")) {

            User fbUser = userService.getUserByFacebookAuth(request.getFacebookRequest()
                    .getFacebookAccessToken());

            if (fbUser == null) {
                user.setUsername(request.getFacebookRequest().getFbEmail());
                user.setFirstName(request.getFacebookRequest().getFbUsername());
                user.setPassword(request.getFacebookRequest().getFacebookUserId());
                user.setProfilePicture(request.getFacebookRequest().getFbProfilePicture());
                user.setFacebookToken(request.getFacebookRequest().getFacebookAccessToken());
                UserRole role = roleService.getByName("USER")
                        .orElseThrow(() -> new UserRoleNotFoundException("USER"));
                user.getRoles().add(role);


                User regUser = userService.registerUser(user);

                String token = generateTokenUsingUsernameAndPassword(
                        request.getFacebookRequest().getFacebookUserId(),
                        regUser.getUsername()
                );

                response.setToken("Bearer "+token);

            }else {
                String jwtToken = generateTokenUsingUsernameAndPassword(
                        request.getFacebookRequest().getFacebookUserId(),
                        fbUser.getUsername()
                );

                response.setToken("Bearer "+jwtToken);
            }

        }






        return ResponseEntity.ok(response);
    }




    private String generateTokenUsingUsernameAndPassword(String password, String username) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }


}












