package com.anmol.jobtrackr.dto;

import com.anmol.jobtrackr.entity.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class JobApplicationRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 120, message = "Company name must be at most 120 characters")
    private String companyName;

    @NotBlank(message = "Role is required")
    @Size(max = 120, message = "Role must be at most 120 characters")
    private String role;

    @Size(max = 80, message = "Location must be at most 80 characters")
    private String location;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    @Size(max = 80, message = "Source must be at most 80 characters")
    private String source;

    @NotNull(message = "Applied date is required")
    @PastOrPresent(message = "Applied date cannot be in the future")
    private LocalDate appliedDate;

    private LocalDate interviewDate;

    @Size(max = 2000, message = "Notes must be at most 2000 characters")
    private String notes;

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }

    public LocalDate getInterviewDate() { return interviewDate; }
    public void setInterviewDate(LocalDate interviewDate) { this.interviewDate = interviewDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}