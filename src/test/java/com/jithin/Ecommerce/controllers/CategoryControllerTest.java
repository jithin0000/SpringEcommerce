package com.jithin.Ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jithin.Ecommerce.models.Category;
import com.jithin.Ecommerce.security.JwtTokenProvider;
import com.jithin.Ecommerce.services.CategoryService;
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

import java.security.Principal;
import java.util.Optional;

import static com.jithin.Ecommerce.utils.CategoryUtils.*;
import static com.jithin.Ecommerce.utils.UserUtils.valid_user;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    public static final String API_CATEGORY = "/api/category";
    private SecurityContext context;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private UsernamePasswordAuthenticationToken authenticationToken;
    private AuthenticationManager authenticationManager;
    private Authentication authentication;
    private Principal principal;
    @Autowired
    private TestRestTemplate template;
    @MockBean
    private CategoryService service;
    private static ObjectMapper om = new ObjectMapper();


    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        authenticationManager = mock(AuthenticationManager.class);
        context = mock(SecurityContext.class);
        principal = mock(Principal.class);

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authentication);
        context.setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(valid_user());
    }

    @Test
    void getAll() {

        when(service.getAll()).thenReturn(category_list());
        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());

        ResponseEntity<String> response = template.exchange(API_CATEGORY, HttpMethod.GET,
                entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getAll();
    }

    @Test
    void createCategory() throws JsonProcessingException {
        when(service.create(any(Category.class))).thenReturn(valid_category());

        String body = om.writeValueAsString(valid_category());
        HttpEntity<?> entity = new HttpEntity<>(body, generateHeaders());
        ResponseEntity<Category> response = template.exchange(API_CATEGORY, HttpMethod.POST, entity,
                Category.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ArgumentCaptor<Category> ac = ArgumentCaptor.forClass(Category.class);
        verify(service, times(1)).create(ac.capture());
        assertEquals(ac.getValue().getName(), response.getBody().getName());
    }

    @Test
    void createCategoryThrowInvalidBoby(){

        Category invalid_category = new Category();
        String body = new Gson().toJson(invalid_category);
        HttpEntity<?> entity = new HttpEntity<>(body, generateHeaders());
        ResponseEntity<String> response = template.exchange(API_CATEGORY, HttpMethod.POST, entity,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ArgumentCaptor<Category> ac = ArgumentCaptor.forClass(Category.class);
        verify(service, times(0)).create(ac.capture());

    }

    @Test
    void getCategory() {

        when(service.getById(anyString())).thenReturn(Optional.of(valid_category()));
        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<Category> response = template.exchange(API_CATEGORY + "/" + CATEGORY_ID,
                HttpMethod.GET,
                entity,
                Category.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).getById(ac.capture());
        assertEquals(ac.getValue(), response.getBody().getId());
    }

    @Test
    void getCategoryByIdThrowException_404(){
        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> response = template.exchange(API_CATEGORY + "/" + "invalid_id",
                HttpMethod.GET,
                entity,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).getById(ac.capture());
        assertTrue(response.getBody().contains(ac.getValue()));

    }

    @Test
    void deleteCategoryById() {

        when(service.deleteById(anyString())).thenReturn("item deleted with this id " + CATEGORY_ID);
        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> response = template.exchange(API_CATEGORY + "/delete/" + CATEGORY_ID, HttpMethod.DELETE,
                entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).deleteById(ac.capture());
        assertTrue(response.getBody().contains(ac.getValue()));
    }

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();

        String token =   jwtTokenProvider.generateToken(authentication);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }

    @Test
    void filterCategoriesByName() {

        when(service.categoriesByName(anyString())).thenReturn(filtered_category_list());

        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> response = template.exchange(
                API_CATEGORY+"/name?search="+CATEGORY_NAME
                , HttpMethod.GET,
                entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).categoriesByName(CATEGORY_NAME);
    }



}









