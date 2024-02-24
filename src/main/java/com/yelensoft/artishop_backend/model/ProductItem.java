package com.yelensoft.artishop_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "nbExemplaire null")
    @Min(value = 1, message = "nbExemplaire, valeur incorrecte")
    private int nbExemplaire;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    @ManyToOne
    private ProductConfig productConfig;

    @ManyToOne
    private Order order;
}
