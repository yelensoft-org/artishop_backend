package com.yelensoft.artishop_backend.model;

import com.yelensoft.artishop_backend.enumClass.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private OrderStatus status;

    private double nbreVote =0.0;

    private double totalValueVote = 0.0;

    private double nbreStar = 0.0;

    @OneToOne
    private Users users;

    @OneToOne
    private UserAddress userAddress;

}
