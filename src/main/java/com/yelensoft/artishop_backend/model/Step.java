package com.yelensoft.artishop_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "champs titre vide")
    @Size(min = 2, max = 30, message = "champs titre, nombre de caractère incorrecte")
    private String title;

    @NotNull(message = "champs nbStep vide")
    @Min(value = 1,message = "champs nbStep, valeur incorrecte")
    private int nbStep;

    @NotBlank(message = "champs videoUrl vide")
    @Size(min = 2, message = "champs videoUrl, nombre de caractère incorrecte")
    private String videoUrl;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    @OneToOne
    private Product product;
}
