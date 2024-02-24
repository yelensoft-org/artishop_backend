package com.yelensoft.artishop_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @NotBlank
    private String content;

    private LocalDateTime creationDate = LocalDateTime.now();

    private boolean deleted = false;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Product product;
}
