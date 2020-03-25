package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.Product;
import com.jithin.Ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ExecutableFindOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.jithin.Ecommerce.utils.ProductUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {


    @Mock
    private ProductRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ProductService SUT;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllProducts(){
        when(repository.findAll()).thenReturn(productList());
        List<Product> result = SUT.getAll();
        assertEquals(4, result.size());
    }

    @Test
    void createProduct(){
        when(repository.save(any(Product.class))).thenReturn(valid_product());
        Product result = SUT.create(valid_product());

        ArgumentCaptor<Product> ac = ArgumentCaptor.forClass(Product.class);
        verify(repository, times(1)).save(ac.capture());
        assertEquals(ac.getValue().getId(), result.getId());

    }

    @Test
    void getProductById(){
        when(repository.findById(anyString())).thenReturn(Optional.of(valid_product()));
        Optional<Product> result = SUT.getById(PRODUCTID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findById(ac.capture());
        assertEquals(ac.getValue(), result.get().getId());


    }

    @Test
    void deleteProductById(){
        doNothing().when(repository).deleteById(PRODUCTID);

        String result = SUT.deleteById(PRODUCTID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).deleteById(ac.capture());
        assertTrue(result.contains(ac.getValue()));

    }

    @Test
    void findProductsByName(){
        when(repository.findByName(any(String.class))).thenReturn(filteredProduct());
        List<Product> result = SUT.filterProductsByName(PRODUCTNAME);

        assertNotNull(result);
        assertEquals(4, result.size());
        ArgumentCaptor<String > ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findByName(ac.capture());

    }


    @Test
    void findAllDistinctColors() {
        when(mongoTemplate.query(Product.class).distinct("colors").all()).thenReturn(valid_colors());

        List<Object> result = SUT.findAllDistinctColors();

        assertEquals(2, result.size());
        verify(mongoTemplate, times(1)).query(Product.class).distinct("colors");
    }

    private List<Object> valid_colors() {
        return Arrays.asList("red","blue");
    }
}