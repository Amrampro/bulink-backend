package com.goulbamc.clientservice.dto;

import com.goulbamc.clientservice.model.Contact;
import com.goulbamc.clientservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRequest {
    private String name;
    private String slogan;
    private String type;
    private Long manager_id;
//    private User manager;
//    private List<Contact> contacts;
    private Date registration_date;
}
