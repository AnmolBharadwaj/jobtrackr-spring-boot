package com.anmol.jobtrackr.service;

import com.anmol.jobtrackr.dto.JobApplicationRequest;
import com.anmol.jobtrackr.entity.ApplicationStatus;
import com.anmol.jobtrackr.entity.JobApplication;
import com.anmol.jobtrackr.exception.ResourceNotFoundException;
import com.anmol.jobtrackr.repository.JobApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository repository;

    @InjectMocks
    private JobApplicationService service;

    @Test
    void findByIdThrowsWhenApplicationDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void createRejectsDuplicateCompanyAndRole() {
        JobApplication existing = new JobApplication();
        existing.setId(1L);
        when(repository.findFirstByCompanyNameIgnoreCaseAndRoleIgnoreCase("Acme", "Java Developer"))
                .thenReturn(Optional.of(existing));

        JobApplicationRequest request = request("Acme", "Java Developer");

        assertThrows(IllegalArgumentException.class, () -> service.create(request));
        verify(repository, never()).save(any(JobApplication.class));
    }

    private JobApplicationRequest request(String company, String role) {
        JobApplicationRequest request = new JobApplicationRequest();
        request.setCompanyName(company);
        request.setRole(role);
        request.setStatus(ApplicationStatus.APPLIED);
        request.setAppliedDate(LocalDate.of(2026, 10, 1));
        return request;
    }
}