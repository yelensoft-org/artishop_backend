package com.yelensoft.artishop_backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yelensoft.artishop_backend.enums.PersonGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 60, message = "invalid number of characters")
    private String fullName;

    @NotBlank
    private String numTel;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;

    private boolean deleted = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updateDate;

    @Enumerated(EnumType.STRING)
    private PersonGender gender;

    @OneToOne
    private Address address;

    @OneToOne
    private Cart cart;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();


}
