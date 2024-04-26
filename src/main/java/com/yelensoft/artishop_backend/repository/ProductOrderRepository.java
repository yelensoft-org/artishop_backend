package com.yelensoft.artishop_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yelensoft.artishop_backend.model.ProductOrder;

import java.util.Optional;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    Optional<ProductOrder> findByIdAndUserId(Long id,Long id_user);
    void deleteByIdAndUserId(Long id,Long id_user);
}
