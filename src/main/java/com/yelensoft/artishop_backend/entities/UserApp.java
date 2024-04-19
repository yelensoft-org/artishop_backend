package com.yelensoft.artishop_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class UserApp extends Person{

    @OneToOne
    private Cart cart;
}
