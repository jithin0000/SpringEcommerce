package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.security.JwtTokenProvider;
import com.jithin.Ecommerce.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;

import static com.jithin.Ecommerce.utils.OrderUtils.order_list;
import static com.jithin.Ecommerce.utils.UserUtils.valid_user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    public static final String API_ORDER = "/api/order";
    @Autowired
    private TestRestTemplate template;

    @MockBean
    private OrderService service;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {

        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(valid_user());
    }

    @Test
    void getAll() {

        when(service.getAll()).thenReturn(order_list());

        HttpEntity<?> entity = new HttpEntity<>(generateHeaders());
        ResponseEntity<String> response =
                template.exchange(API_ORDER, HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getAll();

    }



    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();

        String token =   jwtTokenProvider.generateToken(authentication);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }
}