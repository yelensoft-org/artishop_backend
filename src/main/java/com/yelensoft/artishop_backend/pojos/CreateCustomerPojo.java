package com.yelensoft.artishop_backend.pojos;

import lombok.Data;

@Data
public class CreateCustomerPojo {
    private String fullName;
    private String numTel;
    private String password;
}
