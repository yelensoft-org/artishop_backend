package com.yelensoft.artishop_backend.security;

import com.yelensoft.artishop_backend.entities.Customer;
import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.repositories.CustomerRepository;
import com.yelensoft.artishop_backend.resources.ErrorMessageValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String numTel) throws UsernameNotFoundException {
        try {
            Optional<Customer> customerOptional = customerRepository.findByNumTel(numTel);
            if(customerOptional.isEmpty()){
                throw new UsernameNotFoundException(ErrorMessageValue.USER_NOT_FOUND);
            }
            return new CustomUserDetails(customerOptional.get());
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}