package com.anmol.jobtrackr.dto;

import com.anmol.jobtrackr.entity.ApplicationStatus;

import java.util.Map;

public record ApplicationStatsResponse(
        long totalApplications,
        Map<ApplicationStatus, Long> byStatus
) {
}