package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.exceptions.NotAuthorizedException;
import com.yelensoft.artishop_backend.entities.Cart;
import com.yelensoft.artishop_backend.entities.UserApp;
import com.yelensoft.artishop_backend.pojoClass.AuthPojo;
import com.yelensoft.artishop_backend.repositories.CartRepository;
import com.yelensoft.artishop_backend.repositories.UsersRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CartRepository cartRepository;

    public UserApp addUsers(UserApp userApp){

        UserApp userAppVerif = usersRepository.findByEmail(userApp.getEmail());
        if (userAppVerif != null) throw new NotAuthorizedException("Cet utilisateur existe déjà");
        userApp.setAddress(addressService.addAddress(userApp.getAddress()));
        Cart cart = cartRepository.save(new Cart());
        userApp.setCart(cart);
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return usersRepository.save(userApp);

    }

    public UserApp connectUsers(AuthPojo authPojo){
        UserApp userAppVerif = usersRepository.findByEmail(authPojo.getEmail());
        if (userAppVerif == null) throw new EntityNotFoundException("invalid user");
        if (!passwordEncoder.matches(authPojo.getPassword(), userAppVerif.getPassword())) throw new EntityNotFoundException("invalid password");
        if (userAppVerif.isDeleted()) throw new NotAuthorizedException("denied");
        return userAppVerif;
    }

    public UserApp updateUser(UserApp userApp){
        Optional<UserApp> usersVerif = usersRepository.findById(userApp.getId());
        if (usersVerif.isEmpty()) throw new EntityNotFoundException("invalid user");
        return usersRepository.save(userApp);
    }

    public UserApp deleteUser(Long id){
        Optional<UserApp> usersVerif = usersRepository.findById(id);
        if (usersVerif.isEmpty()) throw new EntityNotFoundException("invalid");
        UserApp userApp = usersVerif.get();
        userApp.setDeleted(!userApp.isDeleted());
        return usersRepository.save(userApp);
    }


    public UserApp getUserById(Long userId) {
    }
}
