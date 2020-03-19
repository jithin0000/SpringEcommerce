package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.Category;
import com.jithin.Ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.jithin.Ecommerce.utils.CategoryUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {


    @Mock
    private CategoryRepository repository;
    @InjectMocks
    private CategoryService SUT;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCategories(){
        when(repository.findAll()).thenReturn(category_list());
        List<Category> result = SUT.getAll();

        assertEquals(4, result.size());
        verify(repository, times(1)).findAll();

    }


    @Test
    void createCategory(){
        when(repository.save(any(Category.class))).thenReturn(category_list().get(0));
        Category result = SUT.create(category_list().get(0));

        ArgumentCaptor<Category> ac = ArgumentCaptor.forClass(Category.class);
        verify(repository, times(1)).save(ac.capture());
        assertEquals(ac.getValue().getName(), result.getName());

    }

    @Test
    void getCategoryById(){
        when(repository.findById(anyString())).thenReturn(Optional.of(valid_category()));

        Optional<Category> result = SUT.getById(CATEGORY_ID);
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findById(ac.capture());
        assertEquals(ac.getValue(), result.get().getId());


    }

    @Test
    void deleteCategoryById(){
        doNothing().when(repository).deleteById(anyString());
        String result = SUT.deleteById(CATEGORY_ID);
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).deleteById(ac.capture());
        assertTrue(result.contains(CATEGORY_ID));
    }


    @Test
    void categoriesByName() {
        when(repository.findByName(anyString())).thenReturn(filtered_category_list());

        List<Category> result = SUT.categoriesByName(CATEGORY_NAME);
        assertEquals(4, result.size());

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findByName(ac.capture());

    }

    @Test
    void updateCategory(){
        when(repository.save(any(Category.class))).thenReturn(updated_category());
        Category result = SUT.update(updated_category());

        assertEquals(UPDATED_CATEGORY, result.getName());
        ArgumentCaptor<Category> ac = ArgumentCaptor.forClass(Category.class);
        verify(repository, times(1)).save(ac.capture());
        assertEquals(CATEGORY_ID, result.getId());
    }


}