package com.yelensoft.artishop_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ProductView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Le nom de l'image est vide")
    @Size(min = 2, message = "champs imageUrl, nombre de caractère incorrecte")
    private String imageUrls;

    @Min(value = 1, message = "valeur nbAvailable incorrecte")
    private int nbAvailable;

    @NotBlank(message = "sizes null")
    @Size(min = 2, message = "sizes, nombre de caractère incorrecte")
    private String sizes;

    @NotBlank(message = "Choisir une couleur")
    @Size(min = 2, message = "couleur, nombre de caractère incorrecte")
    private String color;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    @ManyToOne
    @JsonIgnore
    private Product product;
}
