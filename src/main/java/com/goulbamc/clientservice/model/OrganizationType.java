package com.goulbamc.clientservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "organization_type")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
}
