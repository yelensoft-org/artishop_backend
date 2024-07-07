package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
}
