package com.yelensoft.artishop_backend.repository;

import com.yelensoft.artishop_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
