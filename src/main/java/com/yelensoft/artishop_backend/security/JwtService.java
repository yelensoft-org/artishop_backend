package com.yelensoft.artishop_backend.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yelensoft.artishop_backend.entities.Customer;
import com.yelensoft.artishop_backend.entities.UserRole;
import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.repositories.CustomerRepository;
import com.yelensoft.artishop_backend.resources.ConstanteValues;
import com.yelensoft.artishop_backend.resources.ErrorMessageValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
@Slf4j
public class JwtService {

    private final CustomerRepository customerRepository;

    public JwtService(CustomerRepository authInfoRepository) {
        this.customerRepository = authInfoRepository;
    }
    public String extractNumTel(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    private Claims extractAllClaims(String token) {
       try {
           return Jwts
                   .parserBuilder()
                   .setSigningKey(getSignKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
       }catch (Exception e) {
           throw new BadRequestException(e.getMessage());
       }
    }
    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
       try {
           final String numTel = extractNumTel(token);
           return (numTel.equals(userDetails.getUsername()) && !isTokenExpired(token));
       }catch (Exception e) {
           throw new BadRequestException(e.getMessage());
       }
    }
    public String generateToken(String numTel){
       try {
           Optional<Customer> adminOptional = customerRepository.findByNumTel(numTel);
           if(adminOptional.isPresent()) {
               Map<String, Object> claims = new ObjectMapper().convertValue(adminOptional.get(), new TypeReference<>() {
               });
               claims.put("roles", adminOptional.get().getRoles().stream().map(UserRole::getName).toList());
               return createToken(claims, numTel);
           }
           throw new NotFoundException(ErrorMessageValue.USER_NOT_FOUND);

       }catch (Exception e) {
           throw new BadRequestException(e.getMessage());
       }
    }
    public String generateRefreshToken(String numTel){
        try {
            Optional<Customer> customerOptional = customerRepository.findByNumTel(numTel);
            if(customerOptional.isPresent()) {
                return createRefreshToken(numTel);
            }
            throw new NotFoundException(ErrorMessageValue.USER_NOT_FOUND);

        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    private String createToken(Map<String, Object> claims, String numTel) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(numTel)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ ConstanteValues.TOKEN_LIFETIME))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    private String createRefreshToken(String numTel) {
        try {
            return Jwts.builder()
                    .setSubject(numTel)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ ConstanteValues.REFRESH_TOKEN_LIFETIME))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    private Key getSignKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(ConstanteValues.SECRET);
            return Keys.hmacShaKeyFor(keyBytes);
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}