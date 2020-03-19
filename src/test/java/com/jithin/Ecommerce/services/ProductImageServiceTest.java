package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.Product;
import com.jithin.Ecommerce.models.ProductImage;
import com.jithin.Ecommerce.repository.ProductImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductImageServiceTest {

    public static final String IMAGE_ID = "imageId";
    public static final String NAME = "name";
    @Mock
    private ProductImageRepository repository;
    @InjectMocks
    private ProductImageService SUT;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getAllProductImages(){
        when(repository.findAll()).thenReturn(product_images_list());
        List<ProductImage> result = SUT.getAll();

        assertEquals(4, result.size());

    }

    @Test
    void createProduct(){
        when(repository.save(any(ProductImage.class))).thenReturn(valid_pdt_image());
        ProductImage result = SUT.create(valid_pdt_image());


        ArgumentCaptor<ProductImage> ac = ArgumentCaptor.forClass(ProductImage.class);
        verify(repository, times(1)).save(ac.capture());
        assertEquals(ac.getValue().getId(), result.getId());

    }

    @Test
    void getProductImageById(){
        when(repository.findById(anyString())).thenReturn(Optional.of(valid_pdt_image()));
        Optional<ProductImage> result = SUT.getById(IMAGE_ID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findById(ac.capture());
        assertEquals(ac.getValue(), result.get().getId());


    }

    @Test
    void deleteProductImageById(){

        doNothing().when(repository).deleteById(IMAGE_ID);

        String result = SUT.deleteById(IMAGE_ID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).deleteById(ac.capture());
        assertTrue(result.contains(ac.getValue()));
    }






    private List<ProductImage> product_images_list() {
        List<ProductImage> pdt_image_list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ProductImage image = new ProductImage();
            image.setId(IMAGE_ID +i);
            image.setImageName("new " + NAME +i);
            pdt_image_list.add(image);
        }
        return pdt_image_list;
    }

    public ProductImage valid_pdt_image(){
        ProductImage image = new ProductImage();
        image.setId(IMAGE_ID);
        image.setImageName("new " + NAME);

        return image;
    }

}





















































