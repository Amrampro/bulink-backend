package com.goulbamc.clientservice.dto;

import com.goulbamc.clientservice.model.ContactType;
import com.goulbamc.clientservice.model.Organization;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
    private Long contactId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String contactType;
    //    private ContactType contactType;
//    private Organization organization;
    private Long organization_id;
    private String image;
    private String about;
    private String address;
    private String country;
    private String gender;
    private String website;
    private Date registration_date;
}
