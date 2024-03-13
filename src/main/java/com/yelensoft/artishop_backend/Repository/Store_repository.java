package com.yelensoft.artishop_backend.Repository;

import com.yelensoft.artishop_backend.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Store_repository extends JpaRepository<Store, Integer> {

//    verifier si un artisant exist par son id

    Store findByEmailAndName(String email, String name);
    Store findByUsersId(Long id);
    Store findById(Long id);
    double findByNbreVote(double vote);
    double findByTotalValueVote(double totalVote);
}
