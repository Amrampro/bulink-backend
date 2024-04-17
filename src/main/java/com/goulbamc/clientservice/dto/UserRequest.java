package com.goulbamc.clientservice.dto;

import com.goulbamc.clientservice.model.Organization;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String email;
    private String username;
    private String password;
    private List<Organization> managedOrganizations;
    private String first_name;
    private String last_name;
    private String phone;
    private String image;
    private String about;
    private String address;
    private String country;
    private String gender;
    private Date registration_date;
    private String acc_type;
    private String pin;
}
