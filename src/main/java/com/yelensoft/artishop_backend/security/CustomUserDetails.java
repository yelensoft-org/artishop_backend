package com.yelensoft.artishop_backend.security;

import com.yelensoft.artishop_backend.entities.Customer;
import com.yelensoft.artishop_backend.entities.UserRole;
import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends Customer implements UserDetails {
    private final String numTel;
    private final String password;
    Collection<? extends GrantedAuthority> authorities;
    public CustomUserDetails(Customer customer) {
        try {
            this.numTel = customer.getNumTel();
            this.password= customer.getPassword();
            List<GrantedAuthority> auths = new ArrayList<>();

            for(UserRole role : customer.getRoles()){
                auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
            }
            this.authorities = auths;

        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return numTel;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
