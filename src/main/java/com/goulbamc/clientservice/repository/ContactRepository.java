package com.goulbamc.clientservice.repository;

import com.goulbamc.clientservice.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c ORDER BY c.lastName ASC")
    List<Contact> findAllByOrderByLastNameAsc();

    @Query("SELECT c FROM Contact c WHERE c.contactType = 'CLIENT' ORDER BY c.lastName ASC")
    List<Contact> findAllByClient();

    @Query("SELECT c FROM Contact c WHERE c.contactType = 'EMPLOYEE' ORDER BY c.lastName ASC")
    List<Contact> findAllByEmployee();

    @Query("SELECT c FROM Contact c WHERE c.contactType = 'COMPANY' ORDER BY c.lastName ASC")
    List<Contact> findAllByCompany();

    @Query("SELECT c FROM Contact c WHERE c.contactType = 'OTHER' ORDER BY c.lastName ASC")
    List<Contact> findAllByOther();

//    Contact of a single user connected
    @Query("SELECT c FROM Contact c INNER JOIN Organization o ON c.organization_id = o.orgId INNER JOIN User u ON o.manager_id = u.userId WHERE u.userId = :userId ORDER BY c.lastName ASC")
    List<Contact> findUsersContact(@Param("userId") Long userId);

//    Single user contacts for APK
//@Query("SELECT c FROM Contact c ORDER BY c.lastName ASC")
//List<Contact> findAllByOrderByLastNameAsc();

    @Query("SELECT c FROM Contact c INNER JOIN Organization o ON c.organization_id = o.orgId INNER JOIN User u ON o.manager_id = u.userId WHERE c.contactType = 'CLIENT' AND u.userId = :userId ORDER BY c.lastName ASC")
    List<Contact> findAllByClientFSU(@Param("userId") Long userId);

    @Query("SELECT c FROM Contact c INNER JOIN Organization o ON c.organization_id = o.orgId INNER JOIN User u ON o.manager_id = u.userId WHERE c.contactType = 'EMPLOYEE' AND u.userId = :userId ORDER BY c.lastName ASC")
    List<Contact> findAllByEmployeeFSU(@Param("userId") Long userId);

    @Query("SELECT c FROM Contact c INNER JOIN Organization o ON c.organization_id = o.orgId INNER JOIN User u ON o.manager_id = u.userId WHERE c.contactType = 'COMPANY' AND u.userId = :userId ORDER BY c.lastName ASC")
    List<Contact> findAllByCompanyFSU(@Param("userId") Long userId);

    @Query("SELECT c FROM Contact c INNER JOIN Organization o ON c.organization_id = o.orgId INNER JOIN User u ON o.manager_id = u.userId WHERE c.contactType = 'OTHER' AND u.userId = :userId ORDER BY c.lastName ASC")
    List<Contact> findAllByOtherFSU(@Param("userId") Long userId);

}
