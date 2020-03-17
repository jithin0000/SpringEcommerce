package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.exceptions.UserAlreadyExistException;
import com.jithin.Ecommerce.models.User;
import com.jithin.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<UserRepository, User> {


    @Autowired
    private BCryptPasswordEncoder encoder;

    public User registerUser(User valid_user) {

        User user = getRepository().findByUsername(valid_user.getUsername());

        if (user != null) {
            throw new UserAlreadyExistException("user with this " + valid_user.getUsername() + "already exist");
        }
        valid_user.setPassword(encoder.encode(valid_user.getPassword()));
        return getRepository().save(valid_user);

    }

    public User getUserByGoogleAuth(String idToken){

        return getRepository().findByGoogleToken(idToken);
    }


    public User getUserByFacebookAuth(String idToken) {
        return getRepository().findByFacebookToken(idToken);
    }
}
