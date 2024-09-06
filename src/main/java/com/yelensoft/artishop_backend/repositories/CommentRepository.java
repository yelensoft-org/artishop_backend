package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByStoreId(Long storeId);
  List<Comment> findByProductId(Long productId);
  List<Comment> findByCustomerId(Long userId);
}
