package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.Cart;
import com.jithin.Ecommerce.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jithin.Ecommerce.utils.CartUtils.CART_ID;
import static com.jithin.Ecommerce.utils.CartUtils.cart_list;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository repository;

    @InjectMocks
    private CartService SUT;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCarts(){
        when(repository.findAll()).thenReturn(cart_list());
        List<Cart> result = SUT.getAll();
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createCart(){
        when(repository.save(any(Cart.class))).thenReturn(cart_list().get(0));

        Cart result = SUT.create(cart_list().get(0));

        ArgumentCaptor<Cart> ac = ArgumentCaptor.forClass(Cart.class);
        verify(repository, times(1)).save(ac.capture());
        assertEquals(ac.getValue().getId(), result.getId());

    }

    @Test
    void getCartById(){
        when(repository.findById(anyString())).thenReturn(Optional.of(cart_list().get(0)));
        Optional<Cart> result = SUT.getById(CART_ID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findById(ac.capture());
        assertEquals(ac.getValue(), result.get().getId());

    }


    @Test
    void deleteById(){

        doNothing().when(repository).deleteById(CART_ID);

        String result = SUT.deleteById(CART_ID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).deleteById(ac.capture());
        assertTrue(result.contains(ac.getValue()));
    }



}