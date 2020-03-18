package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.security.JwtTokenProvider;
import com.jithin.Ecommerce.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.Optional;

import static com.jithin.Ecommerce.controllers.UserControllerTest.GENERATED_TOKEN;
import static com.jithin.Ecommerce.utils.UserUtils.USER_ID;
import static com.jithin.Ecommerce.utils.UserUtils.valid_user;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDetailControllerTest {

    public static final String API_USER_DETAILS = "/api/detail";
    @Autowired
    private TestRestTemplate template;
    @MockBean
    private UserService userService;

    private SecurityContext context;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private UsernamePasswordAuthenticationToken authenticationToken;
    private AuthenticationManager authenticationManager;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        authenticationManager = mock(AuthenticationManager.class);
        context = mock(SecurityContext.class);
//        jwtTokenProvider = mock(JwtTokenProvider.class);

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authentication);
        context.setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(valid_user());

    }

    @Test
    void getUser() {

        when(userService.getById(anyString())).thenReturn(Optional.of(valid_user()));

        HttpHeaders header = generateHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);
        ResponseEntity<String> response = template.exchange(API_USER_DETAILS+"/"+USER_ID
                , HttpMethod.GET,
                entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();

       String token =   jwtTokenProvider.generateToken(authentication);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }
}