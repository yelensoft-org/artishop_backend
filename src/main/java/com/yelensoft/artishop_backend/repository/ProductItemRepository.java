package com.yelensoft.artishop_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yelensoft.artishop_backend.model.ProductItem;

import java.util.List;

public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
List<ProductItem> findByCart_Id(Long id);
}
