package com.goulbamc.clientservice.controller;

import com.goulbamc.clientservice.dto.ReminderRequest;
import com.goulbamc.clientservice.dto.ReminderResponse;
import com.goulbamc.clientservice.service.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor

// Documenting using swagger
@Tag(
        name = "CRUD REST APIs for reminders",
        description = "The CRUD API for CREATE, READ, UPDATE and DELETE reminders"
)
public class ReminderController {
    public final ReminderService reminderService;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new reminder"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    public String createReminder(@RequestBody ReminderRequest reminderRequest){ return reminderService.createReminder(reminderRequest); }

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
    public List<ReminderResponse> readAll(){ return reminderService.readReminders(); }

//    reminders for specific user
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Read reminders for specific user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Ok. Example: htt.../user/1"
    )
    public List<ReminderResponse> readUsersReminders(@PathVariable Long userId){ return reminderService.remindersByUser(userId); }


    // Read Single
    @GetMapping("/{reminderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Read single reminder",
            description = "HTTP Status OK"
    )
    public ReminderResponse readReminder(@PathVariable Long reminderId){ return reminderService.readReminder(reminderId); }

    // Put
    @PutMapping("/{reminderId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Update a reminder"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    public String updateReminder(@PathVariable Long reminderId, @RequestBody ReminderRequest updateReminderRequest){
        return reminderService.updateReminder(reminderId, updateReminderRequest);
    }

    // Delete
    @DeleteMapping("/{reminderId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Delete a reminder"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    public String deleteReminder(@PathVariable Long reminderId) { return reminderService.deleteReminder(reminderId); }
}
