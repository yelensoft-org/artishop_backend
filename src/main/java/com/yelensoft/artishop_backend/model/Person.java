package com.yelensoft.artishop_backend.model;

import com.yelensoft.artishop_backend.enumClass.PersonGender;
import com.yelensoft.artishop_backend.enumClass.PersonRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @NotBlank
    protected String fullName;

    @NotBlank
    protected String numTel;

    @NotBlank
    @Email
    protected String email;

    @NotBlank
    protected String password;

    @NotBlank
    protected String ImageUrl;

    protected LocalDateTime creationDate = LocalDateTime.now();

    protected boolean deleted = false;

    protected LocalDateTime updateDate;

    @NotBlank
    @Enumerated(EnumType.STRING)
    protected PersonGender gender;

    @NotBlank
    @Enumerated(EnumType.STRING)
    protected PersonRole role;

    @OneToOne
    protected Address address;
}
