package com.goulbamc.clientservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgId;

    @Column(unique = true, nullable = false)
    private String name;

    private String slogan;
    private String type;
    // other attributes

//    @ManyToOne
//    @JoinColumn(name = "manager_id")
//    private User manager;
    private Long manager_id;

//    @OneToMany(mappedBy = "organization")
//    private List<Contact> contacts = new ArrayList<>();

    private Date registration_date = new Date();
}
