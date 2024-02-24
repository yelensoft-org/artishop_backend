package com.yelensoft.artishop_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Culture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "champs titre vide")
    @Size(min = 2, max = 30, message = "champs titre, nombre de caractère incorrecte")
    private String title;

    @Lob
    @NotBlank(message = "description vide")
    private String description;

    @NotBlank(message = "champs videoUrl vide")
    @Size(min = 2, message = "champs videoUrl, nombre de caractère incorrecte")
    private String videoUrl;

    @NotBlank(message = "champs imageUrl vide")
    @Size(min = 2, message = "champs imageUrl, nombre de caractère incorrecte")
    private String imageUrl;

    @NotBlank(message = "champs region vide")
    @Size(min = 2, max = 30, message = "champs region, nombre de caractère incorrecte")
    private String region;

    @NotBlank(message = "champs ethnie vide")
    @Size(min = 2, max = 30, message = "champs ethnie, nombre de caractère incorrecte")
    private String ethnie;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    private boolean deleted = false;

    @ManyToOne
    private Admins admins;
}
