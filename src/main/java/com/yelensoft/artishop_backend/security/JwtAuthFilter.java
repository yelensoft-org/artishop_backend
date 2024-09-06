package com.yelensoft.artishop_backend.security;

import com.yelensoft.artishop_backend.resources.ConstanteValues;
import com.yelensoft.artishop_backend.resources.URIs;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    UserDetailsServiceImpl userDetailsServiceImpl;
    JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtService = jwtService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(request.getServletPath().equals(URIs.REFRESH_TOKEN_URI)) {
                filterChain.doFilter(request, response);
            }
            else {
                String authHeader = request.getHeader(ConstanteValues.AUTHORIZATION);
                String token = null;
                String username = null;
                if(authHeader != null && authHeader.startsWith(ConstanteValues.BEARER)){
                    token = authHeader.substring(ConstanteValues.BEARER_TEXT_LENGTH);
                    username = jwtService.extractNumTel(token);
                }

                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                    if(jwtService.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
                filterChain.doFilter(request, response);
            }
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
