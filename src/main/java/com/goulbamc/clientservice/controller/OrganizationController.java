package com.goulbamc.clientservice.controller;

import com.goulbamc.clientservice.dto.OrganizationRequest;
import com.goulbamc.clientservice.dto.OrganizationResponse;
import com.goulbamc.clientservice.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor

// Documenting using swagger
@Tag(
        name = "CRUD REST APIs for organizations",
        description = "The CRUD API for CREATE, READ, UPDATE and DELETE organizations"
)
public class OrganizationController {
    public final OrganizationService organizationService;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new organization"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    public String create(@RequestBody OrganizationRequest organizationRequest) { return organizationService.createOrganization(organizationRequest); }

    // ReadAll
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Read all"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Ok"
    )
    public List<OrganizationResponse> readAll() { return organizationService.readAll(); }

//    User Organization
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrganizationResponse> userOrganization(@PathVariable Long userId) { return organizationService.userOrganization(userId); }

    // Read Single
    @GetMapping("/{orgId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Read single organization",
            description = "HTTP Status OK"
    )
    public OrganizationResponse readSingle(@PathVariable Long orgId) { return organizationService.readSingle(orgId); }

// Put
    @PutMapping("/{orgId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Update a organization"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    public String update(@PathVariable Long orgId, @RequestBody OrganizationRequest organizationRequest){
        return organizationService.updateOrg(orgId, organizationRequest);
    }

    // Delete
    @DeleteMapping("/{orgId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Delete an organization"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    public String delete(@PathVariable Long orgId){ return organizationService.deleteOrg(orgId); }
}
