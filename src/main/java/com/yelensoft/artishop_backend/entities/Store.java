package com.yelensoft.artishop_backend.entities;

import com.yelensoft.artishop_backend.enums.StoreStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private LocalDate creationDate = LocalDate.now();

    private LocalDate updateDate;

    private boolean deleted = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StoreStatus status = StoreStatus.CLOSE;

    private double nbreVote = 0.0;

    private double totalValueVote = 0.0;

    private double nbreStar = 0.0;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Address userAddress;

}
