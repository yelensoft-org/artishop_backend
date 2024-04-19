package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByUserId(Long artisanId);

    Optional<Store> findByIdAndUserId(Long storeId, Long userId);
}
