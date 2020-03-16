package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.exceptions.UserAlreadyExistException;
import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.jithin.Ecommerce.utils.UserUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {


    @Mock
    private UserRepository repository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService SUT;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);

    }

    @Test
    void getRepository() {

        UserRepository result = SUT.getRepository();
        assertNotNull(result);

    }

    @Test
    void registerUserSuccessFull(){
        when(repository.save(any(User.class))).thenReturn(valid_user());
        when(repository.findByUsername(anyString())).thenReturn(null);
        User result = SUT.registerUser(valid_user());

        assertNotNull(result);

        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        verify(repository, times(1)).save(ac.capture());
        assertEquals(ac.getValue().getId(), result.getId());
    }


    @Test
    void registerUserThrowsUserAlreadyExistException(){
        when(repository.findByUsername(anyString())).thenReturn(valid_user());

        Exception exception = assertThrows(UserAlreadyExistException.class, () -> {
            User result = SUT.registerUser(valid_user());
        });

        assertTrue(exception.getMessage().contains(valid_user().getUsername()));
        verify(repository, times(0)).save(any(User.class));
    }





}