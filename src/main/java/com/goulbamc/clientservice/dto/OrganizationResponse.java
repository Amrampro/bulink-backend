package com.goulbamc.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationResponse {
    private Long orgId;
    private String name;
    private String slogan;
    private String type;
    private Long manager_id;
    private Date registration_date;
}
