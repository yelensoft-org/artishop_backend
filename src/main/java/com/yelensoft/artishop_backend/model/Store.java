package com.yelensoft.artishop_backend.model;

import com.yelensoft.artishop_backend.enumClass.StoreStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String numTel1;

    @NotBlank
    private String numTel2;

    @NotBlank
    @Email
    private String email;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    private boolean deleted = false;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    private double nbreVote =0.0;

    private double totalValueVote = 0.0;

    private double nbreStar = 0.0;

    @OneToOne
    private Users users;

    @OneToOne
    private Address address;

}
