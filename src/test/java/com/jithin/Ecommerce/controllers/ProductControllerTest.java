package com.jithin.Ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jithin.Ecommerce.exceptions.ProductNotFoundException;
import com.jithin.Ecommerce.models.Product;
import com.jithin.Ecommerce.security.JwtTokenProvider;
import com.jithin.Ecommerce.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static com.jithin.Ecommerce.utils.ProductUtils.*;
import static com.jithin.Ecommerce.utils.UserUtils.valid_user;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    public static final String API_PRODUCT = "/api/product";
    @MockBean
    private ProductService service;
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private Authentication authentication;
    private static final ObjectMapper om = new ObjectMapper();


    @BeforeEach
    void setUp() {
        authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(valid_user());
    }

    @Test
    void getAll() {
        when(service.getAll()).thenReturn(productList());
        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> response = template.exchange(API_PRODUCT, HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getAll();
    }

    @Test
    void createProduct() throws JsonProcessingException {
        when(service.create(any(Product.class))).thenReturn(valid_product());

        String body = om.writeValueAsString(valid_product());
        HttpEntity<?> entity = new HttpEntity<>(body, generateHeaders());

        ResponseEntity<String> response = template
                .exchange(API_PRODUCT, HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ArgumentCaptor<Product> ac = ArgumentCaptor.forClass(Product.class);
        verify(service, times(1)).create(ac.capture());
        assertTrue(response.getBody().contains(ac.getValue().getId()));

    }

    @Test
    void createProduct_400() throws JsonProcessingException {

        String body = om.writeValueAsString(new Product());
        HttpEntity<?> entity = new HttpEntity<>(body, generateHeaders());

        ResponseEntity<String> response = template.exchange(API_PRODUCT, HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(0)).create(any(Product.class));

    }



    @Test
    void getProductById(){
        when(service.getById(anyString())).thenReturn(Optional.of(valid_product()));

        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> response = template
                .exchange(API_PRODUCT+"/"+PRODUCTID, HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).getById(ac.capture());
        assertTrue(response.getBody().contains(ac.getValue()));
    }

    @Test
    void getProductById_404(){
        when(service.getById(anyString())).thenThrow(new ProductNotFoundException("invalid_id"));

        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> response = template
                .exchange(API_PRODUCT+"/"+"invalid_id", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).getById(ac.capture());
        assertTrue(response.getBody().contains(ac.getValue()));
    }

    @Test
    void deleteProductById(){
        when(service.deleteById(anyString())).thenReturn("deleted item with this id "+PRODUCTID);
        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> result = template.exchange(API_PRODUCT + "/delete/" + PRODUCTID,
                HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).deleteById(ac.capture());
        assertTrue(result.getBody().contains(ac.getValue()));


    }

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();

        String token =   jwtTokenProvider.generateToken(authentication);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }
}


















