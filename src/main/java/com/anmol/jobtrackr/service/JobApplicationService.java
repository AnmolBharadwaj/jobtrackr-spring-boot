package com.anmol.jobtrackr.service;

import com.anmol.jobtrackr.dto.ApplicationStatsResponse;
import com.anmol.jobtrackr.dto.JobApplicationRequest;
import com.anmol.jobtrackr.dto.JobApplicationResponse;
import com.anmol.jobtrackr.entity.ApplicationStatus;
import com.anmol.jobtrackr.entity.JobApplication;
import com.anmol.jobtrackr.exception.ResourceNotFoundException;
import com.anmol.jobtrackr.repository.JobApplicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public JobApplicationResponse create(JobApplicationRequest request) {
        assertNoDuplicate(request.getCompanyName(), request.getRole(), null);

        JobApplication application = new JobApplication();
        copyRequestToEntity(request, application);
        return toResponse(repository.save(application));
    }

    @Transactional(readOnly = true)
    public Page<JobApplicationResponse> findAll(ApplicationStatus status, String q, Pageable pageable) {
        Page<JobApplication> page;

        if (status != null) {
            page = repository.findByStatus(status, pageable);
        } else if (q != null && !q.isBlank()) {
            page = repository.findByCompanyNameContainingIgnoreCaseOrRoleContainingIgnoreCase(
                    q.trim(), q.trim(), pageable
            );
        } else {
            page = repository.findAll(pageable);
        }

        return page.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public JobApplicationResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public JobApplicationResponse update(Long id, JobApplicationRequest request) {
        JobApplication application = findEntity(id);
        assertNoDuplicate(request.getCompanyName(), request.getRole(), id);
        copyRequestToEntity(request, application);
        return toResponse(repository.save(application));
    }

    public void delete(Long id) {
        JobApplication application = findEntity(id);
        repository.delete(application);
    }

    @Transactional(readOnly = true)
    public ApplicationStatsResponse stats() {
        Map<ApplicationStatus, Long> byStatus = new EnumMap<>(ApplicationStatus.class);
        Arrays.stream(ApplicationStatus.values())
                .forEach(status -> byStatus.put(status, repository.countByStatus(status)));

        return new ApplicationStatsResponse(repository.count(), byStatus);
    }

    private JobApplication findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found: " + id));
    }

    private void assertNoDuplicate(String companyName, String role, Long currentId) {
        repository.findFirstByCompanyNameIgnoreCaseAndRoleIgnoreCase(companyName.trim(), role.trim())
                .ifPresent(existing -> {
                    if (currentId == null || !existing.getId().equals(currentId)) {
                        throw new IllegalArgumentException("An application for this company and role already exists");
                    }
                });
    }

    private void copyRequestToEntity(JobApplicationRequest request, JobApplication application) {
        application.setCompanyName(request.getCompanyName().trim());
        application.setRole(request.getRole().trim());
        application.setLocation(clean(request.getLocation()));
        application.setStatus(request.getStatus());
        application.setSource(clean(request.getSource()));
        application.setAppliedDate(request.getAppliedDate());
        application.setInterviewDate(request.getInterviewDate());
        application.setNotes(clean(request.getNotes()));
    }

    private String clean(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private JobApplicationResponse toResponse(JobApplication application) {
        return new JobApplicationResponse(
                application.getId(),
                application.getCompanyName(),
                application.getRole(),
                application.getLocation(),
                application.getStatus(),
                application.getSource(),
                application.getAppliedDate(),
                application.getInterviewDate(),
                application.getNotes(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }
}