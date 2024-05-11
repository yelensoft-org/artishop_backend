package com.yelensoft.artishop_backend.repositories;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByStoreId(Long storeId);
  List<Comment> findByProductId(Long productId);
  List<Comment> findByUserAppId(Long userId);
}
