package com.goulbamc.clientservice.controller;

import com.goulbamc.clientservice.dto.UserResponse;
import com.goulbamc.clientservice.dto.UserRequest;
import com.goulbamc.clientservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

// Documenting using swagger
@Tag(
        name = "CRUD REST APIs for clients",
        description = "The CRUD API for CREATE, READ, UPDATE and DELETE clients"
)
public class UserController {

    public final UserService userService;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // Swagger documentation
    @Operation(
            summary = "Create a client"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    public String createclient(@RequestBody UserRequest userRequest){ return userService.createclient(userRequest); }

    // ReadAll
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Read/Fetch all clients"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    public List<UserResponse> readClientsSortedByLastName() {
        return userService.readClientsSortedByLastName();
    }
//    public List<UserResponse> readclients(){ return clientService.readclients(); }

    // ReadSingle
    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Read/Fetch a single client"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    public UserResponse readclient(@PathVariable Long clientId){ return userService.readclient(clientId);}

    // Put
    @PutMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Update a client"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    public String updateclient(@PathVariable Long clientId, @RequestBody UserRequest updatedUserRequest) {
        return userService.updateclient(clientId, updatedUserRequest);
    }

    // Delete
    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Delete a client"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    public String deleteclient(@PathVariable Long clientId){ return userService.deleteClient(clientId); }
}
