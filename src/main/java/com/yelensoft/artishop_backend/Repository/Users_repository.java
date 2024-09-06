package com.yelensoft.artishop_backend.Repository;

import com.yelensoft.artishop_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users_repository extends JpaRepository<Customer, Long> {
   Customer findUsersById(Long id);

}
