package com.yelensoft.artishop_backend.repository;

import com.yelensoft.artishop_backend.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
}
