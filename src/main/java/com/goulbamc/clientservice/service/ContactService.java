package com.goulbamc.clientservice.service;

import com.goulbamc.clientservice.dto.ContactRequest;
import com.goulbamc.clientservice.dto.ContactResponse;
import com.goulbamc.clientservice.model.Contact;
import com.goulbamc.clientservice.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {
    private final ContactRepository contactRepository;
    private final OrganizationService organizationService;

    /****************************************************************************
     *                            Tests functions                               *
     ****************************************************************************/
    // Testing if id exist
    public boolean dataExists(Long id) {
        if (contactRepository.existsById(id)) {
            return true;
        } else {
            log.info("Contact " + id + " does not exist");
            return false;
        }
    }

    // Retrieving clients information
    private ContactResponse mapToContactResponse(Contact contact) {
        return ContactResponse.builder()
                .contactId(contact.getContactId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .contactType(contact.getContactType())
                .organization_id(contact.getOrganization_id())
                .about(contact.getAbout())
                .address(contact.getAddress())
                .country(contact.getCountry())
                .gender(contact.getGender())
                .website(contact.getWebsite())
                .registration_date(contact.getRegistration_date())
                .build();
    }

    /**************************************************************
     *                       CRUD Functions                       *
     **************************************************************/

    // Create a new Contact
    public String create(ContactRequest contactRequest) {
        String message = "Success ! Contact created successfully";
        boolean status = true;

        if (contactRequest.getLastName() == null || contactRequest.getLastName() == "" || contactRequest.getPhone() == null || contactRequest.getPhone() == ""){
            return "Last name and phone number MUST be provided";
        } else if (contactRequest.getOrganization_id() == null || organizationService.dataExists(contactRequest.getOrganization_id()) == false) {
            return "Error: Organization must be valid";
        } else {
            String ctype = contactRequest.getContactType();
            if (ctype == null || ctype == ""){
                ctype = "CLIENT";
            }
            try {
                Contact contact = Contact.builder()
                        .firstName(contactRequest.getFirstName())
                        .lastName(contactRequest.getLastName())
                        .email(contactRequest.getEmail())
                        .phone(contactRequest.getPhone())
                        .contactType(ctype)
                        .organization_id(contactRequest.getOrganization_id())
                        .about(contactRequest.getAbout())
                        .address(contactRequest.getAddress())
                        .country(contactRequest.getCountry())
                        .gender(contactRequest.getGender())
                        .website(contactRequest.getWebsite())
                        .registration_date(new Date())
                        .build();

                contactRepository.save(contact);
                return message;
            } catch (Exception e){
                log.info("Error: " + e.getMessage());
                return "An error occured. Verify informations and retry";
            }
        }
    }

    // All contacts By Id
    public List<ContactResponse> findAll(){
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Read all Contacts
    public List<ContactResponse> findAllByOrderByLastNameAsc(){
        List<Contact> contacts = contactRepository.findAllByOrderByLastNameAsc();
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Find all by client
    public List<ContactResponse> findAllByClient(){
        List<Contact> contacts = contactRepository.findAllByClient();
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Find all by company
    public List<ContactResponse> findAllByCompany(){
        List<Contact> contacts = contactRepository.findAllByCompany();
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Find all by employee
    public List<ContactResponse> findAllByEmployee(){
        List<Contact> contacts = contactRepository.findAllByEmployee();
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Find all by client
    public List<ContactResponse> findAllByOther(){
        List<Contact> contacts = contactRepository.findAllByOther();
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Find all by client
    public List<ContactResponse> findAllByClientFSU(Long userId){
        List<Contact> contacts = contactRepository.findAllByClientFSU(userId);
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Find all by company
    public List<ContactResponse> findAllByCompanyFSU(Long userId){
        List<Contact> contacts = contactRepository.findAllByCompanyFSU(userId);
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Find all by employee
    public List<ContactResponse> findAllByEmployeeFSU(Long userId){
        List<Contact> contacts = contactRepository.findAllByEmployeeFSU(userId);
        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Find all by client
    public List<ContactResponse> findAllByOtherFSU(Long userId){
        List<Contact> contacts = contactRepository.findAllByOtherFSU(userId);
        return contacts.stream().map(this::mapToContactResponse).toList();
    }


    // Read Single contact
    public ContactResponse readSingle(Long id){
        if (dataExists(id)){
            Contact contact = contactRepository.findById(id).get();
            return mapToContactResponse(contact);
        } else {
            return null;
        }
    }

//    Contacts of a given user
    public List<ContactResponse> findUsersContact(Long userId){
        List<Contact> contacts = contactRepository.findUsersContact(userId);
        return contacts.stream().map(this::mapToContactResponse).toList();
    }


    // Update

    // Delete Contact
    public String deleteContact(Long id){
        if (dataExists(id)){
            contactRepository.deleteById(id);
            return "Contact deleted successfully !";
        } else {
            return "The contact searched do not exist";
        }
    }
}
