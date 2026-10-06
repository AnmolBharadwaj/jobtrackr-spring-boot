package com.anmol.jobtrackr.dto;

import com.anmol.jobtrackr.entity.ApplicationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record JobApplicationResponse(
        Long id,
        String companyName,
        String role,
        String location,
        ApplicationStatus status,
        String source,
        LocalDate appliedDate,
        LocalDate interviewDate,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}