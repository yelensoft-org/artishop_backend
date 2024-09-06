package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.pojos.AuthPojo;
import com.yelensoft.artishop_backend.pojos.CreateCustomerPojo;
import com.yelensoft.artishop_backend.dto.JwtResponseDto;
import com.yelensoft.artishop_backend.entities.Customer;
import com.yelensoft.artishop_backend.enums.PersonGender;
import com.yelensoft.artishop_backend.enums.RoleName;
import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.exceptions.NotAuthorizedException;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.exceptions.ResourceExistException;
import com.yelensoft.artishop_backend.mappers.Mapper;
import com.yelensoft.artishop_backend.repositories.CustomerRepository;
import com.yelensoft.artishop_backend.repositories.UserRoleRepository;
import com.yelensoft.artishop_backend.resources.ConstanteValues;
import com.yelensoft.artishop_backend.resources.ErrorMessageValue;
import com.yelensoft.artishop_backend.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private CustomerRepository customerRepository;
    private Mapper mapper;
    private UserRoleRepository userRoleRepository;
    @Override
    public ResponseEntity<?> createCustomer(CreateCustomerPojo createCustomerPojo) {
        Optional<Customer> customerOptional = customerRepository.findByNumTel(createCustomerPojo.getNumTel());
        if(customerOptional.isPresent()) throw new ResourceExistException("Un utilisateur avec le même numéro existe déjà");
        else {
            try {
                Customer customer = Customer.builder()
                        .fullName(createCustomerPojo.getFullName())
                        .numTel(createCustomerPojo.getNumTel())
                        .password(passwordEncoder.encode(createCustomerPojo.getPassword()))
                        .roles(Set.of(userRoleRepository.findByName(RoleName.CUSTOMER.name())
                                .orElseThrow(() -> new NotFoundException(ErrorMessageValue.ROLE_NOT_FOUND))))
                        .creationDate(new Date())
                        .updateDate(new Date())
                        .deleted(false)
                        .gender(PersonGender.MAN)
                        .build();
                return ResponseHandler.generateResponse("Ajout reussi",
                        HttpStatus.CREATED,
                        mapper.customerToCustomerCreated(customerRepository.save(customer)));
            }catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
        }
    }

    @Override
    public ResponseEntity<?> loginCustomer(AuthPojo authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getNumTel(),
                        authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            JwtResponseDto body = JwtResponseDto.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getNumTel()))
                    .refreshToken(jwtService.generateRefreshToken(authRequestDTO.getNumTel()))
                    .build();
            return ResponseHandler.generateResponse("Connexion de l'utilisateur", HttpStatus.OK, body);
        } else {
            throw new UsernameNotFoundException(ErrorMessageValue.USER_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(ConstanteValues.AUTHORIZATION);
        if(authHeader != null && authHeader.startsWith(ConstanteValues.BEARER)) {
            String refreshToken = authHeader.substring(ConstanteValues.BEARER_TEXT_LENGTH);
            String username = jwtService.extractNumTel(refreshToken);
            return ResponseHandler.generateResponse("Creation d'un nouveau access token", HttpStatus.OK, JwtResponseDto.builder()
                    .accessToken(jwtService.generateToken(username)).refreshToken(refreshToken).build());
            /*return ResponseEntity.ok(ResponseMessage.builder()
                    .body(JwtResponseDTO.builder()
                            .accessToken(jwtService.GenerateToken(username)).refreshToken(refreshToken).build())
                    .status(HttpStatus.OK.value())
                    .date(LocalDateTime.now())
                    .path(URIs.AUTH_PREFIX+URIs.REFRESH_TOKEN_URI)
                    .message("Creation d'un nouveau access token")
                    .error(null)
                    .build());*/
        }
        throw new NotAuthorizedException(ErrorMessageValue.ACCESS_FORBIDDEN);
    }
}
