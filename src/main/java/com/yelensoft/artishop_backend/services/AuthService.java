package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.pojos.AuthPojo;
import com.yelensoft.artishop_backend.pojos.CreateCustomerPojo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> createCustomer(CreateCustomerPojo createCustomerPojo);
    ResponseEntity<?> loginCustomer(AuthPojo authRequestDTO);
    ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
