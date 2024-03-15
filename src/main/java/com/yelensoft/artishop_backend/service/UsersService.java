package com.yelensoft.artishop_backend.service;

import com.yelensoft.artishop_backend.model.Cart;
import com.yelensoft.artishop_backend.model.Users;
import com.yelensoft.artishop_backend.pojoClass.AuthPojo;
import com.yelensoft.artishop_backend.repository.CartRepository;
import com.yelensoft.artishop_backend.repository.UsersRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Users addUsers(Users users){

        Users usersVerif = usersRepository.findByEmail(users.getEmail());
        if (usersVerif != null) throw new EntityExistsException("exist");
        users.setAddress(addressService.addAddress(users.getAddress()));
        Cart cart = cartRepository.save(new Cart());
        users.setCart(cart);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersRepository.save(users);

    }

    public Users connectUsers(AuthPojo authPojo){
        Users usersVerif = usersRepository.findByEmail(authPojo.getEmail());
        if (usersVerif == null) throw new EntityNotFoundException("invalid user");
        if (!passwordEncoder.matches(authPojo.getPassword(), usersVerif.getPassword())) throw new EntityNotFoundException("invalid password");
        return usersVerif;
    }
}
