package com.yelensoft.artishop_backend.model;

import com.yelensoft.artishop_backend.enumClass.OrderStatus;
import com.yelensoft.artishop_backend.enumClass.SizeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "name null")
    @Size(min = 2, max = 30, message = "invalid number of characters")
    private String name;

    @Lob
    @NotBlank(message = "description vide")
    private String description;

    @NotBlank(message = "champs imageUrl vide")
    @Size(min = 2, message = "champs imageUrl, nombre de caractère incorrecte")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "sizeType vide")
    private SizeType sizeType;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    private boolean deleted = false;




}
