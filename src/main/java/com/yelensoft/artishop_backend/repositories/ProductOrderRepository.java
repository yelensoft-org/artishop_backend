package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    Optional<ProductOrder> findByIdAndUserAppId(Long id, Long id_user);

    void deleteByIdAndUserAppId(Long id, Long id_user);

    ProductOrder getByIdAndUserAppId(Long id, Long id_user);

    List<ProductOrder> findByUserAppIdAndCreationDateBetween(Long userApp_id, LocalDateTime start, LocalDateTime end);
}
