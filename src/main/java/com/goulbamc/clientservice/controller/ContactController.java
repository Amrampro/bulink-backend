package com.goulbamc.clientservice.controller;

import com.goulbamc.clientservice.dto.ContactRequest;
import com.goulbamc.clientservice.dto.ContactResponse;
import com.goulbamc.clientservice.model.Contact;
import com.goulbamc.clientservice.service.ContactService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor

// Documenting using swagger
@Tag(
        name = "CRUD REST APIs for contacts",
        description = "The CRUD API for CREATE, READ, UPDATE and DELETE contacts"
)
public class ContactController {
    public final ContactService contactService;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // Swagger documentation
    @Operation(
            summary = "Create a contact"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    public String create(@RequestBody ContactRequest contactRequest) { return contactService.create(contactRequest); }

    // ReadAll
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Read/Fetch all contacts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    public List<ContactResponse> readAll(){ return contactService.findAllByOrderByLastNameAsc();}

    // ReadAll Only clients
    @GetMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> readAllClients() { return contactService.findAllByClient(); }

    // ReadAll Only companies
    @GetMapping("/companies")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> readAllCompanies() { return contactService.findAllByCompany(); }

    // ReadAll Only employees
    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> readAllEmployees() { return contactService.findAllByEmployee(); }

    // ReadAll Only others
    @GetMapping("/others")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> readAllOthers() { return contactService.findAllByOther(); }

    // ReadAll Only clients
    @GetMapping("/clientsFSU/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> readAllClientsFSU(@PathVariable Long userId) { return contactService.findAllByClientFSU(userId); }

    // ReadAll Only companies
    @GetMapping("/companiesFSU/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> readAllCompaniesFSU(@PathVariable Long userId) { return contactService.findAllByCompanyFSU(userId); }

    // ReadAll Only employees
    @GetMapping("/employeesFSU/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> readAllEmployeesFSU(@PathVariable Long userId) { return contactService.findAllByEmployeeFSU(userId); }

    // ReadAll Only others
    @GetMapping("/othersFSU/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> readAllOthersFSU(@PathVariable Long userId) { return contactService.findAllByOtherFSU(userId); }

    // ReadAll Contacts of a given user
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> findUserContacts(@PathVariable Long userId) { return contactService.findUsersContact(userId); }

    // ReadSingle
    @GetMapping("/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Read/Fetch a single contact"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    public ContactResponse readSingle(@PathVariable Long contactId){ return contactService.readSingle(contactId); }

    // Delete
    @DeleteMapping("/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    // Swagger documentation
    @Operation(
            summary = "Delete a contact"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    public String deleteSingle(@PathVariable Long contactId){ return contactService.deleteContact(contactId); }

//    Export Data as CSV
@GetMapping("/csvexport/{userId}")
public void exportCSV(HttpServletResponse response, @PathVariable Long userId)
        throws Exception {

    //set file name and content type
    String filename = "contacts-data.csv";

    response.setContentType("text/csv");
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + filename + "\"");
    //create a csv writer
    StatefulBeanToCsv<ContactResponse> writer = new StatefulBeanToCsvBuilder<ContactResponse>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false)
            .build();
    //write all employees data to csv file
    writer.write(contactService.findUsersContact(userId));

}
}
