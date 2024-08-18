package com.yelensoft.artishop_backend.repositories;

import com.yelensoft.artishop_backend.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserApp,Long> {

    UserApp findByEmail(String email);


    UserApp findUserAppById(long idU);
}
