package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByUserAppIdAndStoreId(Long userId,Long storeId);
    List<Follow> findByStoreId(Long storeId);
}
