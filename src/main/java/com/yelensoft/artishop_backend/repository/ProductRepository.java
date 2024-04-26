package com.yelensoft.artishop_backend.repository;

import com.yelensoft.artishop_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndStoreIdAndStoreUserIdAndDeletedFalse(Long productId, Long storeId, Long userId);

    List<Product> findAllByStoreIdAndDeletedFalse(Long storeId);
    List<Product> findAllByDeletedFalse();

    Optional<Product> findByIdAndStoreIdAndDeletedFalse(Long productId, Long storeId);
    Optional<Product> findByIdAndDeletedFalse(long id);
}
