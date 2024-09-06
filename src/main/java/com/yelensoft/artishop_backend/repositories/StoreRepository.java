package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByCustomerId(Long artisanId);

    Optional<Store> findByIdAndCustomerId(Long storeId, Long userId);

    Optional<Store> findByEmailAndName(String email, String name);

    Optional<Store> findByName(String name);
}
