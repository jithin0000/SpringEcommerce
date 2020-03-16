package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.jithin.Ecommerce.utils.UserUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomUserDetailServicesTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private CustomUserDetailServices SUT;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsername_returns_user() {

        when(repository.findByUsername(anyString())).thenReturn(valid_user());

        UserDetails result = SUT.loadUserByUsername(USERNAME);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findByUsername(ac.capture());

        assertEquals(ac.getValue(), result.getUsername());

    }

    @Test
    void loadByUserNameThrowUsernameNotFoundException(){
        when(repository.findByUsername(anyString())).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            SUT.loadUserByUsername(anyString());
        });

        assertTrue(exception != null);
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).findByUsername(ac.capture());

        assertTrue(exception.getMessage().contains(ac.getValue()));
    }

    @Test
    void loadUserByIdSuccess(){
        when(repository.getById(anyString())).thenReturn(valid_user());
        User result = SUT.loadByUserId(USER_ID);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).getById(ac.capture());

        assertEquals(ac.getValue(), result.getId());


    }

    @Test
    void loadByUserIdThrowsExeption(){
        when(repository.getById(anyString())).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            SUT.loadByUserId(anyString());
        });

        assertTrue(exception != null);
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        verify(repository, times(1)).getById(ac.capture());

        assertTrue(exception.getMessage().contains(ac.getValue()));

    }
}

















