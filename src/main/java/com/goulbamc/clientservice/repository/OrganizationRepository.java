package com.goulbamc.clientservice.repository;

import com.goulbamc.clientservice.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Query("SELECT o FROM Organization o WHERE o.manager_id = :userId")
    List<Organization> userOrganization(@Param("userId") Long userId);
}
