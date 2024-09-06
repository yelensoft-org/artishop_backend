package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.entities.UserRole;
import com.yelensoft.artishop_backend.enums.RoleName;
import com.yelensoft.artishop_backend.repositories.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataInitializationService {
    private UserRoleRepository userRoleRepository;

    public void createDefaultRole() {
        if (userRoleRepository.count() == 0) {
            UserRole customerRole = new UserRole();
            UserRole artisanRole = new UserRole();
            customerRole.setName(RoleName.CUSTOMER.name());
            artisanRole.setName(RoleName.ARTISAN.name());
            userRoleRepository.save(customerRole);
            userRoleRepository.save(artisanRole);
        }
    }
}
