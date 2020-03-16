package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.exceptions.UserAlreadyExistException;
import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailServices implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = repository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("no user with this username or email " + s);
        }

        return user;
    }

    @Transactional
    public User loadByUserId(String id) throws UsernameNotFoundException{
        User user = repository.getById(id);

        if (user == null) {
            throw new UsernameNotFoundException("no user with this username or email " + id);
        }

        return user;

    }






}
