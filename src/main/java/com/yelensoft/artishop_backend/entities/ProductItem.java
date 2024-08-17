package com.yelensoft.artishop_backend.entities;

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

    @NotNull(message = "Choisi un nombre d'exemplaire")
    @Min(value = 1, message = "Exemplaire incorrecte")
    private int nbExemplaire;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    @ManyToOne
    private ProductView productView;

    @ManyToOne
    private ProductOrder productOrder;

    @ManyToOne
    private Cart cart;
}
