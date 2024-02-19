package com.yelensoft.artishop_backend.model;

import com.yelensoft.artishop_backend.enumClass.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String tittle;

    @NotBlank String content;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime creationDate = LocalDateTime.now();

    private boolean deleted = false;

    @ManyToOne
    private Person person;
}
