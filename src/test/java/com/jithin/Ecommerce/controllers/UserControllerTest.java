package com.jithin.Ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jithin.Ecommerce.dto.InvalidLoginResponse;
import com.jithin.Ecommerce.dto.LoginRequestDto;
import com.jithin.Ecommerce.dto.RegisterRequestDto;
import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.models.UserRole;
import com.jithin.Ecommerce.security.JwtTokenProvider;
import com.jithin.Ecommerce.services.RoleService;
import com.jithin.Ecommerce.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jithin.Ecommerce.utils.UserUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    public static final String API_USER = "/api/user";
    public static final String VALID_USERNAME = "emai1l@gmail.com";
    public static final String VALID_PASSWORD = "456adfsf";
    public static final String GENREATED_TOKEN = "genreated_token";
    public static final String GENERATED_TOKEN = "generated_token";
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @Autowired
    private TestRestTemplate template;
    private static final ObjectMapper om = new ObjectMapper();
    private SecurityContext context;
    private JwtTokenProvider jwtTokenProvider;
    private UsernamePasswordAuthenticationToken authenticationToken;
    private AuthenticationManager authenticationManager;
    private Authentication authentication;


    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        authenticationManager = mock(AuthenticationManager.class);
        context = mock(SecurityContext.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);

    }

    @Test
    void createUser() throws JsonProcessingException {

        when(roleService.getByName("USER")).thenReturn(Optional.of(valid_user_role()));
        when(userService.registerUser(any(User.class))).thenReturn(valid_user());

        HttpHeaders headers = getHttpHeaders();

        String body = om.writeValueAsString(register_request());
        HttpEntity<?> entity = new HttpEntity<>(body, headers);


        ResponseEntity<String> response = template
                .exchange(API_USER + "/register", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).registerUser(ac.capture());
//        assertTrue(response.getBody().contains(ac.getValue().getId()));
    }

    private UserRole valid_user_role() {
        UserRole role = new UserRole();
        role.setName("USER");
        return role;
    }

    private RegisterRequestDto register_request() {

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setUsername(USERNAME);
        dto.setPassword(PASSWORD);
        dto.setFirstName(FIRSTNAME);
        List<String> roleList = new ArrayList<>();
        roleList.add("USER");
        dto.setRoles(roleList);
        return dto;

    }

    @Test
    void createUserInvalidBody(){

        User invalid_register = new User();

        ResponseEntity<String> response = template.postForEntity(API_USER + "/register", invalid_register, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, times(0)).registerUser(invalid_register);

    }

    @Test
    void loginUserSuccess() throws JsonProcessingException {

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authentication);
        context.setAuthentication(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(GENERATED_TOKEN);


        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername(VALID_USERNAME);
        loginRequestDto.setPassword(VALID_PASSWORD);
        String body = om.writeValueAsString(loginRequestDto);

        HttpEntity entity = new HttpEntity(body, getHttpHeaders());

         ResponseEntity<String> response =  template
                 .exchange(API_USER + "/login", HttpMethod.POST, entity, String.class);

         assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Bearer "));

    }

    @Test
    void loginUserSuccessUnauthorized() throws JsonProcessingException {

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authentication);
        context.setAuthentication(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(GENERATED_TOKEN);


        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("invalid_username");
        loginRequestDto.setPassword("invalid_oasswrd");
        String body = om.writeValueAsString(loginRequestDto);

        HttpEntity entity = new HttpEntity(body, getHttpHeaders());

        ResponseEntity<InvalidLoginResponse> response =  template
                .postForEntity(API_USER + "/login",loginRequestDto, InvalidLoginResponse.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        assertTrue(response.getBody() instanceof InvalidLoginResponse);
    }

    @Test
    void loginRequestInvalidBody400(){

        LoginRequestDto invalid_login = new LoginRequestDto();

        ResponseEntity<String> response = template.postForEntity(API_USER + "/login",
                invalid_login, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

    }




    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}










