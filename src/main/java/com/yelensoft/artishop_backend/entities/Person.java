package com.yelensoft.artishop_backend.entities;

import com.yelensoft.artishop_backend.enumClass.PersonGender;
import com.yelensoft.artishop_backend.enumClass.PersonRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank
    @Size(min = 2, max = 60, message = "invalid number of characters")
    protected String fullName;

    @NotBlank
    protected String numTel;

    @NotBlank
    @Email
    protected String email;

    @NotBlank
    protected String password;

    @NotBlank
    protected String imageUrl;

    protected LocalDateTime creationDate = LocalDateTime.now();

    protected boolean deleted = false;

    protected LocalDateTime updateDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    protected PersonGender gender;

    @Enumerated(EnumType.STRING)
    protected PersonRole role;

    @OneToOne
    protected Address address;


}
