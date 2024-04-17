package com.goulbamc.clientservice.service;

import com.goulbamc.clientservice.dto.UserRequest;
import com.goulbamc.clientservice.dto.UserResponse;
import com.goulbamc.clientservice.model.Organization;
import com.goulbamc.clientservice.model.User;
import com.goulbamc.clientservice.repository.OrganizationRepository;
import com.goulbamc.clientservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    /****************************************************************************
     *                            Tests functions                               *
     ****************************************************************************/
    // Testing if id exist
    public boolean dataExist(Long id) {
        if (userRepository.existsById(id)) {
            return true;
        } else {
            log.info("User " + id + " does not exist");
            return false;
        }
    }

    // Generating random 5 digits
    public static String getAlphaNumericString(int n)
    {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    // Retrieving clients information
    private UserResponse mapToClientResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .phone(user.getPhone())
                .image(user.getImage())
                .about(user.getAbout())
                .address(user.getAddress())
                .country(user.getCountry())
                .gender(user.getGender())
                .registration_date(user.getRegistration_date())
                .pin(user.getPin())
                .build();
    }

    /**************************************************************
     *                       CRUD Functions                       *
     **************************************************************/

    // Create a new client
    public String createclient(UserRequest userRequest) {
        String message = "User created successfully";
        boolean status = true;

        if (userRequest.getEmail() == null || userRequest.getUsername() == null || userRequest.getPassword() == null){
            message = "Email, Username and Password must be valid";
            status = false;
        }

        log.info("Creation status: " + status);

        if (status == true) {
            try {
                User user = User.builder()
                        .email(userRequest.getEmail())
                        .username(userRequest.getUsername())
                        .password(userRequest.getPassword())
                        .first_name(userRequest.getFirst_name())
                        .last_name(userRequest.getLast_name())
                        .phone(userRequest.getPhone())
                        .image(userRequest.getImage())
                        .about(userRequest.getAbout())
                        .address(userRequest.getAddress())
                        .country(userRequest.getCountry())
                        .gender(userRequest.getGender())
                        .registration_date(new Date())
                        .pin(userRequest.getPin())
                        .build();

                userRepository.save(user);
                log.info("User " + user.getUserId() + " is saved successfully");

//            Creating organization
                String orgName = userRequest.getUsername() +"."+ getAlphaNumericString(7);
                User manager = userRepository.findById(user.getUserId()).orElse(null);
                Organization organization = Organization.builder()
                        .name(orgName)
                        .manager_id(user.getUserId())
                        .registration_date(new Date())
                        .build();

                organizationRepository.save(organization);
                return message + " And Organization " + orgName;
            } catch (DataIntegrityViolationException e) {
                log.info("Error : " + e.getMessage());
//                return ""+ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
//                return "Error in creation: Email or username already exist ";
                if (e.getMessage().contains("UK_email")) {
                    return ""+ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
                } else if (e.getMessage().contains("UK_username")) {
                    return ""+ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
                } else {
                    return ""+ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate entry [Either email or password]. Please check your data");
                }
            } catch (Exception e) {
                return "Error in creation: " + e.getMessage();
            }
        } else {
            return message;
        }
    }

    // Read all clients

    public List<UserResponse> readClientsSortedByLastName() {
        List<User> users = userRepository.findAllByOrderByLastNameAsc();
        return users.stream().map(this::mapToClientResponse).toList();
    }
//    public List<UserResponse> readclients() {
//        List<User> clients;
//        clients = userRepository.findAll();
//
//        return clients.stream().map(this::mapToClientResponse).toList();
//    }

    // Read single client
    public UserResponse readclient(Long clientId) {
        if (dataExist(clientId)) {
            User user = userRepository.findById(clientId).get();
            return mapToClientResponse(user);
        } else {
            return null;
        }
    }

    public String updateclient(Long id, UserRequest updatedUserRequest) {
        boolean status = true;
        if (dataExist(id)) {
            User existingUser = userRepository.findById(id).orElse(null);

            if (existingUser != null) {
                log.info("Creation status: " + status);

                if (status) {
                    if (updatedUserRequest.getUsername() != null) {
                        existingUser.setUsername(updatedUserRequest.getUsername());
                    }

                    if (updatedUserRequest.getEmail() != null && !updatedUserRequest.getEmail().isEmpty()) {
                        existingUser.setEmail(updatedUserRequest.getEmail());
                    }

                    if (updatedUserRequest.getFirst_name() != null && !updatedUserRequest.getFirst_name().isEmpty()) {
                        existingUser.setFirst_name(updatedUserRequest.getFirst_name());
                    }

                    if (updatedUserRequest.getLast_name() != null && !updatedUserRequest.getLast_name().isEmpty()) {
                        existingUser.setLast_name(updatedUserRequest.getLast_name());
                    }

                    if (updatedUserRequest.getPhone() != null && !updatedUserRequest.getPhone().isEmpty()) {
                        existingUser.setPhone(updatedUserRequest.getPhone());
                    }

                    if (updatedUserRequest.getImage() != null && !updatedUserRequest.getImage().isEmpty()) {
                        existingUser.setImage(updatedUserRequest.getImage());
                    }

                    if (updatedUserRequest.getAbout() != null && !updatedUserRequest.getAbout().isEmpty()) {
                        existingUser.setAbout(updatedUserRequest.getAbout());
                    }

                    if (updatedUserRequest.getAddress() != null && !updatedUserRequest.getAddress().isEmpty()) {
                        existingUser.setAddress(updatedUserRequest.getAddress());
                    }

                    if (updatedUserRequest.getCountry() != null && !updatedUserRequest.getCountry().isEmpty()) {
                        existingUser.setCountry(updatedUserRequest.getCountry());
                    }

                    if (updatedUserRequest.getGender() != null && !updatedUserRequest.getGender().isEmpty()) {
                        existingUser.setGender(updatedUserRequest.getGender());
                    }

                    if (updatedUserRequest.getPin() != null){
                        existingUser.setPin(updatedUserRequest.getPin());
                    }

                    if (updatedUserRequest.getPassword() != null){
                        existingUser.setPassword(updatedUserRequest.getPassword());
                    }

                    userRepository.save(existingUser);
                    log.info("User " + id + " is updated successfully");
                    return "User " + id + " is updated successfully";
                }
            }
        } else {
            return "The user does not exist";
        }

        return "An error occurred";
    }

    // Delete client
    public String deleteClient(Long id){
        if (dataExist(id)) {
            userRepository.deleteById(id);
            return "User deleted successfully !";
        } else {
            return "The user searched do not exist";
        }
    }
}
