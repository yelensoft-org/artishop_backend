package com.yelensoft.artishop_backend.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AuthPojo {
    private String numTel;
    private String password;
}
