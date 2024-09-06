package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    Optional<ProductOrder> findByIdAndCustomerId(Long id, Long customerId);

    void deleteByIdAndCustomerId(Long id, Long customerId);

    ProductOrder getByIdAndCustomerId(Long id, Long customerId);

    List<ProductOrder> findByCustomerIdAndCreationDateBetween(Long customerId, LocalDateTime start, LocalDateTime end);
}
