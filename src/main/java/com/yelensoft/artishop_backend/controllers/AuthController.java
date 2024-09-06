package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.dto.AuthDTO;
import com.yelensoft.artishop_backend.dto.CreateCustomerDto;
import com.yelensoft.artishop_backend.resources.URIs;
import com.yelensoft.artishop_backend.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URIs.AUTH_PREFIX)
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping(URIs.LOGIN_URI)
    @Operation(summary = "Connection du client à son compte")
    public ResponseEntity<?> loginCustomer(@RequestBody AuthDTO authDTO) {
        return authService.loginCustomer(authDTO);
    }

    @PostMapping(URIs.CREATE_CUSTOMER_URI)
    @Operation(summary = "Inscription d'un nouveau client")
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        return authService.createCustomer(createCustomerDto);
    }

    @GetMapping(URIs.REFRESH_TOKEN_URI)
    @Operation(summary = "Obtenir un nouveau access token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.refreshToken(request, response);
    }

}
