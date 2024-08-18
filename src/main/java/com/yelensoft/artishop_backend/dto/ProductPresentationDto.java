package com.yelensoft.artishop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPresentationDto {
    Long id;
    String nom;
    double price;
    String image;
    boolean isInCart;
}
