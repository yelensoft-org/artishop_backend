package com.yelensoft.artishop_backend.Repository;

import com.yelensoft.artishop_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
