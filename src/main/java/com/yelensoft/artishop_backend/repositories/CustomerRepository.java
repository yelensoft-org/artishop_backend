package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByNumTel(String numTel);
}
