package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    //just comment
}
