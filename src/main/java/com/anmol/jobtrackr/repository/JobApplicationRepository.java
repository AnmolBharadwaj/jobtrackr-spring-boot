package com.anmol.jobtrackr.repository;

import com.anmol.jobtrackr.entity.ApplicationStatus;
import com.anmol.jobtrackr.entity.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByStatus(ApplicationStatus status, Pageable pageable);

    Page<JobApplication> findByCompanyNameContainingIgnoreCaseOrRoleContainingIgnoreCase(
            String companyName,
            String role,
            Pageable pageable
    );

    long countByStatus(ApplicationStatus status);

    Optional<JobApplication> findFirstByCompanyNameIgnoreCaseAndRoleIgnoreCase(
            String companyName,
            String role
    );
}