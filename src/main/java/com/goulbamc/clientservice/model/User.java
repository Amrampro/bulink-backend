package com.goulbamc.clientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

//    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    private String password;

    private String first_name;
    private String last_name;
    private String phone;
    private String image;
    private String about;
    private String address;
    private String country;
    private String gender;
    private Date registration_date = new Date();

    private String pin;
//    private String linkedin;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}