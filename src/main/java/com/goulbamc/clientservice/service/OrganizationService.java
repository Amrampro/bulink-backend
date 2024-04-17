package com.goulbamc.clientservice.service;

import com.goulbamc.clientservice.dto.OrganizationRequest;
import com.goulbamc.clientservice.dto.OrganizationResponse;
import com.goulbamc.clientservice.model.Organization;
import com.goulbamc.clientservice.model.User;
import com.goulbamc.clientservice.repository.OrganizationRepository;
import com.goulbamc.clientservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.OrientationRequested;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserService userService;

    /****************************************************************************
     *                            Tests functions                               *
     ****************************************************************************/
    // Testing if data is available
    public boolean dataExists(Long id) {
        if (organizationRepository.existsById(id)) {
            return true;
        } else {
            return false;
        }
    }

    // Retrieving data
    private OrganizationResponse mapToOrganizationResponse(Organization organization) {
        return OrganizationResponse.builder()
                .orgId(organization.getOrgId())
                .name(organization.getName())
                .slogan(organization.getSlogan())
                .type(organization.getType())
                .manager_id(organization.getManager_id())
                .registration_date(organization.getRegistration_date())
                .build();
    }

    /**************************************************************
     *                       CRUD Functions                       *
     **************************************************************/

    // Create organization
    public String createOrganization(OrganizationRequest organizationRequest){
        String message = "Organization created";
        boolean status = true;

        if (organizationRequest.getName() == null){
            message = "Name required!";
            status = false;
        }

        if (organizationRequest.getManager_id() == null || userService.dataExist(organizationRequest.getManager_id()) == false){
            message = "Manager(User) does not exist";
            status = false;
        }

        log.info("Creation status : " + status);

//        User manager = userRepository.findById(userId).orElse(null);

        if (status == true){

            Organization organization = Organization.builder()
                    .name(organizationRequest.getName())
                    .manager_id(organizationRequest.getManager_id())
                    .slogan(organizationRequest.getSlogan())
                    .type(organizationRequest.getType())
                    .registration_date(new Date())
                    .build();

            organizationRepository.save(organization);
            return message;
        } else {
            return message;
        }
    }

    // User Organization
    public List<OrganizationResponse> userOrganization(Long userId){
        List<Organization> organizations = organizationRepository.userOrganization(userId);
        return organizations.stream().map(this::mapToOrganizationResponse).toList();
    }

    // ReadAll
    public List<OrganizationResponse> readAll(){
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream().map(this::mapToOrganizationResponse).toList();
    }

    // ReadSingle
    public OrganizationResponse readSingle(Long orgId){
        if (dataExists(orgId)){
            Organization organization = organizationRepository.findById(orgId).get();
            return mapToOrganizationResponse(organization);
        } else {
            return null;
        }
    }

    // Update an organization
    public String updateOrg(Long id, OrganizationRequest orgRequest){
        boolean status = true;
        if (dataExists(id)){
//            User manager = userRepository.findById().orElse(null);
            Organization existOrg = organizationRepository.findById(id).orElse(null);

            if (existOrg != null){
                if (orgRequest.getName() != null) {
                    existOrg.setName(orgRequest.getName());
                }

                if (orgRequest.getSlogan() != null) {
                    existOrg.setSlogan(orgRequest.getSlogan());
                }

                if (orgRequest.getType() != null) {
                    existOrg.setType(orgRequest.getType());
                }

//            if (orgRequest.getManager() != null && orgRequest.getManager() != manager){
//                existOrg.setManager(manager);
//            }
            }

            organizationRepository.save(existOrg);
            return "Organization modified successfully";
//            return orgRequest.getSlogan();
        } else {
            return "Organization not founded";
        }
    }

    // Delete Organization
    public String deleteOrg(Long id){
        if (dataExists(id)) {
            organizationRepository.deleteById(id);
            return "Organization deleted successfully !";
        } else {
            return "Organization searched does not exist";
        }
    }
}
