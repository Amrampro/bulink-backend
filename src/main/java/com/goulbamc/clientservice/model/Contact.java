package com.goulbamc.clientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    private String firstName;

    @NotBlank
    private String lastName;

    private String email;

    @NotBlank
    private String phone;

//    @Enumerated(EnumType.STRING)
//    private ContactType contactType;
    private String contactType;

//    @ManyToOne
//    @JoinColumn(name = "organization_id")
//    private Organization organization;
    private Long organization_id;

    private String about;
    private String image;
    private String address;
    private String country;
    private String gender;
    private String website;
    private Date registration_date = new Date();
}
