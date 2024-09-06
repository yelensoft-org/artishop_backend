package com.yelensoft.artishop_backend.entities;

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
    private Customer customer;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Product product;

    public void setText(String text) {
        this.content = text;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getText() {
        return content;
    }
}
