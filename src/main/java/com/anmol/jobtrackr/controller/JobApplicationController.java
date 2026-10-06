package com.anmol.jobtrackr.controller;

import com.anmol.jobtrackr.dto.ApplicationStatsResponse;
import com.anmol.jobtrackr.dto.JobApplicationRequest;
import com.anmol.jobtrackr.dto.JobApplicationResponse;
import com.anmol.jobtrackr.entity.ApplicationStatus;
import com.anmol.jobtrackr.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponse> create(@Valid @RequestBody JobApplicationRequest request) {
        JobApplicationResponse created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public Page<JobApplicationResponse> findAll(
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10, sort = "appliedDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return service.findAll(status, q, pageable);
    }

    @GetMapping("/stats")
    public ApplicationStatsResponse stats() {
        return service.stats();
    }

    @GetMapping("/{id}")
    public JobApplicationResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public JobApplicationResponse update(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}