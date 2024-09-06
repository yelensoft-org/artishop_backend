package com.yelensoft.artishop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AuthDTO {
    private String numTel;
    private String password;
}
