package com.yelensoft.artishop_backend.entities;

import com.yelensoft.artishop_backend.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull(message = "nbProductItem null")
    @Min(value = 1, message = "nbProductItem, valeur incorrecte")
    private int nbProductItem;

    @NotNull(message = "total montant est null")
    @DecimalMin(value = "0.0", message = "totalAmount, valeur incorrecte")
    private double totalAmount = 0.0;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime updateDate;

    private boolean deleted = false;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Address address;

    @ManyToOne
    private PaymentMethod paymentMethod;
}
