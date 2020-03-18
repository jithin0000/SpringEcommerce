package com.jithin.Ecommerce.services;

import com.jithin.Ecommerce.models.UserRole;
import com.jithin.Ecommerce.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService extends BaseService<RoleRepository, UserRole> {
    public Optional<UserRole> getByName(String roleName) {
        return getRepository().findByName(roleName);
    }
}
