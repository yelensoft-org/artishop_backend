package com.yelensoft.artishop_backend.model;

import com.yelensoft.artishop_backend.enumClass.AdminsLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public class Admins extends Person{

    @NotBlank
    @Enumerated(EnumType.STRING)
    private AdminsLevel level;
}
