package com.jithin.Ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jithin.Ecommerce.dto.CartRequestDto;
import com.jithin.Ecommerce.exceptions.CartNotFoundException;
import com.jithin.Ecommerce.models.Cart;
import com.jithin.Ecommerce.security.JwtTokenProvider;
import com.jithin.Ecommerce.services.CartService;
import com.jithin.Ecommerce.services.CustomUserDetailServices;
import com.jithin.Ecommerce.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static com.jithin.Ecommerce.utils.CartUtils.CART_ID;
import static com.jithin.Ecommerce.utils.CartUtils.cart_list;
import static com.jithin.Ecommerce.utils.ProductUtils.PRODUCTID;
import static com.jithin.Ecommerce.utils.ProductUtils.valid_product;
import static com.jithin.Ecommerce.utils.UserUtils.USER_ID;
import static com.jithin.Ecommerce.utils.UserUtils.valid_user;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    public static final String API_CART = "/api/cart";
    @Autowired
    private TestRestTemplate template;

    @MockBean
    private CartService service;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private Authentication authentication;
    private static ObjectMapper om = new ObjectMapper();
    @MockBean
    private CustomUserDetailServices userService;
    @MockBean
    private ProductService productService;


    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(valid_user());
    }

    @Test
    void getCart() {

        when(service.getById(anyString())).thenReturn(Optional.of(cart_list().get(0)));


        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<Cart> response = template.exchange(API_CART + "/" + CART_ID, HttpMethod.GET,
                entity, Cart.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).getById(ac.capture());
        assertEquals(ac.getValue(), response.getBody().getId());
    }

    @Test
    void getCart_400() {


        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> response = template.exchange(API_CART + "/" + "invalid id", HttpMethod.GET,
                entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("invalid id"));
    }

    @Test
    void addToCart() throws JsonProcessingException {
        when(userService.loadByUserId(anyString())).thenReturn(valid_user());
        when(productService.getById(anyString())).thenReturn(Optional.of(valid_product()));
        when(service.create(any(Cart.class))).thenReturn(cart_list().get(0));

        CartRequestDto cart_request = new CartRequestDto();
        cart_request.setProductId(PRODUCTID);
        cart_request.setUserId(USER_ID);


        String body = om.writeValueAsString(cart_request) ;
        HttpEntity<?> entity = new HttpEntity<>(body, generateHeaders());
        ResponseEntity<Cart> response = template.exchange(API_CART + "/add",
                HttpMethod.POST, entity, Cart.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ArgumentCaptor<Cart> ac = ArgumentCaptor.forClass(Cart.class);
        verify(service, times(1)).create(ac.capture());
        verify(userService, times(2)).loadByUserId(USER_ID);
        verify(productService, times(1)).getById(PRODUCTID);


    }

    // FIXME: 19/03/20 need to add two more methods to check remove product




    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();

        String token =   jwtTokenProvider.generateToken(authentication);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }
}
















