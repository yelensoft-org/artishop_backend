package com.yelensoft.artishop_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yelensoft.artishop_backend.model.ProductItem;

public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {

}
