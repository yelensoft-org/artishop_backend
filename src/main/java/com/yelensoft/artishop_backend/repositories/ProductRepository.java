package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndStoreIdAndStoreUserId(Long productId, Long storeId, Long userId);

    List<Product> findAllByStoreId(Long storeId);

    Optional<Product> findByIdAndStoreId(Long productId, Long storeId);
}
