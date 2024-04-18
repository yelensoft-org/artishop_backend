package com.yelensoft.artishop_backend.pojoClass;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthPojo {

    @NotBlank(message = "email null or empty")
    private String email;

    @NotBlank(message = "password null or empty")
    private String password;

}
