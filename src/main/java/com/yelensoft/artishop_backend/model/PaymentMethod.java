package com.yelensoft.artishop_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "name null")
    @Size(min = 2, max = 30, message = "invalid number of characters")
    private String name;

    @NotBlank(message = "champs imageUrl vide")
    @Size(min = 2, message = "champs imageUrl, nombre de caractère incorrecte")
    private String imageUrl;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    private boolean deleted = false;
}
