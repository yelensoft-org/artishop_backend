package com.yelensoft.artishop_backend.repository;

import com.yelensoft.artishop_backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);
}
