package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.EOrder;
import com.jithin.Ecommerce.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.jithin.Ecommerce.utils.OrderUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository repository;
    @InjectMocks
    private OrderService SUT;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllOrders(){
        when(repository.findAll()).thenReturn(order_list());
        List<EOrder> result = SUT.getAll();
        assertEquals(4, result.size());
        verify(repository, times(1)).findAll();

    }

    @Test
    void createOrder(){
        when(repository.save(any(EOrder.class))).thenReturn(valid_order());
        EOrder result = SUT.create(valid_order());

        ArgumentCaptor<EOrder> ac = ArgumentCaptor.forClass(EOrder.class);
        verify(repository, times(1)).save(ac.capture());

        assertEquals(ac.getValue().getId(), result.getId());
    }

    @Test
    void getOrderById(){
        when(repository.findById(anyString())).thenReturn(Optional.of(valid_order()));
        Optional<EOrder> result = SUT.getById(ORDER_ID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findById(ac.capture());
        assertEquals(ac.getValue(), result.get().getId());

    }

    @Test
    void deleteOrderById() {
        doNothing().when(repository).deleteById(anyString());
        String result = SUT.deleteById(ORDER_ID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).deleteById(ac.capture());
        assertTrue(result.contains(ac.getValue()));

    }



}