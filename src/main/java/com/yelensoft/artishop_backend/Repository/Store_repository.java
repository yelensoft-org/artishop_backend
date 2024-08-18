package com.yelensoft.artishop_backend.Repository;

import com.yelensoft.artishop_backend.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Store_repository extends JpaRepository<Store, Long> {

//    verifier si un artisant exist par son id

    Store findByEmailAndName(String email, String name);
    Store findStoreByName(String name);
    Store findStoreById(Long id);
    double findByNbreVote(double vote);
    double findByTotalValueVote(double totalVote);
}
