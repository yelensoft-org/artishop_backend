package com.yelensoft.artishop_backend.entities;

import com.yelensoft.artishop_backend.enums.AdminsLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Admins extends Person{

    @NotBlank
    @Enumerated(EnumType.STRING)
    private AdminsLevel level;
}
