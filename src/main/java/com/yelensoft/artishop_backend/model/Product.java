package com.yelensoft.artishop_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "name null")
    @Size(min = 2, max = 30, message = "invalid number of characters")
    private String name;

    @NotNull(message = "price null")
    @DecimalMin(value = "0.1", message = "price, valeur incorrecte")
    private double price;

    @NotNull
    @Min(value = 1)
    private int stockQuantity;

    private boolean published = false;

    @Lob
    @NotBlank(message = "description vide")
    private String description;

    @NotBlank
    private String globalSize;

    private boolean available = true;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    private boolean deleted = false;

    @ManyToOne
    private Store store;
}
