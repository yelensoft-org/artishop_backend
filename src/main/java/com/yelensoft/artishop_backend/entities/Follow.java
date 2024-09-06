package com.yelensoft.artishop_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Follow {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Store store;
}
