package com.yelensoft.artishop_backend.entities;

import com.yelensoft.artishop_backend.enumClass.MoneyUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "name null")
    @Size(min = 2, max = 30, message = "invalid number of characters")
    private String name;

    @NotNull
    @DecimalMin(value = "0.1", message = "price, valeur incorrecte")
    private double price;

    @Min(value = 1)
    private int quantity;

    @Enumerated(EnumType.STRING)
    private MoneyUnit priceUnit;


    private boolean published = false;

    @NotNull
    private int nbLike = 0;

    private String quantityUnit;

    @Lob
    @NotBlank(message = "description vide")
    private String description;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate = LocalDateTime.now();

    private boolean deleted = false;

    @NotNull
    @ManyToOne
    private Store store;

    @NotNull
    @ManyToMany
    private List<Category> categoryIds;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductView> productViews;
}
