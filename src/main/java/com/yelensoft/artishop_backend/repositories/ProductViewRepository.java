package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Long> {
    Optional<ProductView> findByIdAndProductIdAndProductStoreIdAndProductStoreUserAppId(Long productViewId, Long productId, Long storeId, Long userId);

    List<ProductView> findAllByProductId(Long productId);
}
