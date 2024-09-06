package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.exceptions.NotAuthorizedException;
import com.yelensoft.artishop_backend.entities.Cart;
import com.yelensoft.artishop_backend.entities.Customer;
import com.yelensoft.artishop_backend.pojoClass.AuthPojo;
import com.yelensoft.artishop_backend.repositories.CartRepository;
import com.yelensoft.artishop_backend.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CartRepository cartRepository;

    public Customer addUsers(Customer customer){

        Optional<Customer> customerOptional = customerRepository.findByEmail(customer.getEmail());
        if (customerOptional.isEmpty()) throw new NotAuthorizedException("Cet utilisateur existe déjà");
        customer.setAddress(addressService.addAddress(customer.getAddress()));
        Cart cart = cartRepository.save(new Cart());
        customer.setCart(cart);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);

    }

    public Customer connectUsers(AuthPojo authPojo){
        Optional<Customer> customerOptional = customerRepository.findByEmail(authPojo.getEmail());
        if (customerOptional .isEmpty()) throw new EntityNotFoundException("invalid user");
        if (!passwordEncoder.matches(authPojo.getPassword(), customerOptional.get().getPassword())) throw new EntityNotFoundException("invalid password");
        if (customerOptional.get().isDeleted()) throw new NotAuthorizedException("denied");
        return customerOptional.get();
    }

    public Customer updateUser(Customer customer){
        Optional<Customer> usersVerif = customerRepository.findById(customer.getId());
        if (usersVerif.isEmpty()) throw new EntityNotFoundException("invalid user");
        return customerRepository.save(customer);
    }

    public Customer deleteUser(Long id){
        Optional<Customer> usersVerif = customerRepository.findById(id);
        if (usersVerif.isEmpty()) throw new EntityNotFoundException("invalid");
        Customer customer = usersVerif.get();
        customer.setDeleted(!customer.isDeleted());
        return customerRepository.save(customer);
    }


    public Customer getUserById(Long userId) {
        return customerRepository.findById(userId).orElse(null);
    }
}
