package com.yelensoft.artishop_backend.model;

import com.yelensoft.artishop_backend.enumClass.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "status vide")
    private OrderStatus status;

    @NotNull(message = "nbProductItem null")
    @Min(value = 1, message = "nbProductItem, valeur incorrecte")
    private int nbProductItem;

    @NotNull(message = "totalAmount null")
    @DecimalMin(value = "0.1", message = "totalAmount, valeur incorrecte")
    private double totalAmount;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    private boolean deleted = false;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Address address;

    @ManyToOne
    private PaymentMethod paymentMethod;
}
