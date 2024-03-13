package com.yelensoft.artishop_backend.Repository;

import com.yelensoft.artishop_backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users_repository extends JpaRepository<Users, Integer> {
   Users findById(Long id);

}
