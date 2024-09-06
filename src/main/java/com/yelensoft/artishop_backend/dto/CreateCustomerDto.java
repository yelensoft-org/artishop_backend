package com.yelensoft.artishop_backend.dto;

import lombok.Data;

@Data
public class CreateCustomerDto {
    private String fullName;
    private String numTel;
    private String password;
}
