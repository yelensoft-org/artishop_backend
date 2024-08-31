package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findById(long id);
    List<Category> findAllById(long categoryId);
}
