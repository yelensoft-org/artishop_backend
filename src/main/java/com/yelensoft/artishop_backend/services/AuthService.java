package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.dto.AuthDTO;
import com.yelensoft.artishop_backend.dto.CreateCustomerDto;
import com.yelensoft.artishop_backend.entities.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> createCustomer(CreateCustomerDto createCustomerDto);
    ResponseEntity<?> loginCustomer(AuthDTO authRequestDTO);
    ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
