package com.yelensoft.artishop_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ProductView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "champs imageUrl vide")
    @Size(min = 2, message = "champs imageUrl, nombre de caractère incorrecte")
    private String imageUrls;

    @NotNull(message = "nbAvailable null")
    @Min(value = 1, message = "valeur nbAvailable incorrecte")
    private int nbAvailable;

    @NotBlank(message = "sizes null")
    @Size(min = 2, message = "sizes, nombre de caractère incorrecte")
    private String sizes;

    @NotBlank(message = "color null")
    @Size(min = 2, message = "color, nombre de caractère incorrecte")
    private String color;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    @ManyToOne
    @JsonIgnore
    private Product product;
}
