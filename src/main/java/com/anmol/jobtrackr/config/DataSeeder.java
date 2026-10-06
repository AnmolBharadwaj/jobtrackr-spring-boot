package com.anmol.jobtrackr.config;

import com.anmol.jobtrackr.entity.ApplicationStatus;
import com.anmol.jobtrackr.entity.JobApplication;
import com.anmol.jobtrackr.repository.JobApplicationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(JobApplicationRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            JobApplication one = application("Apex Systems", "Java Backend Developer", "Gurugram", ApplicationStatus.INTERVIEW, "LinkedIn", 4, "Technical round scheduled.");
            JobApplication two = application("BrightStack", "Software Engineer", "Remote", ApplicationStatus.APPLIED, "Company Website", 2, "Resume submitted.");
            JobApplication three = application("CloudNova", "Backend Engineer", "Bengaluru", ApplicationStatus.SCREENING, "Referral", 1, "Recruiter screening completed.");

            repository.saveAll(List.of(one, two, three));
        };
    }

    private JobApplication application(
            String company,
            String role,
            String location,
            ApplicationStatus status,
            String source,
            int daysAgo,
            String notes
    ) {
        JobApplication application = new JobApplication();
        application.setCompanyName(company);
        application.setRole(role);
        application.setLocation(location);
        application.setStatus(status);
        application.setSource(source);
        application.setAppliedDate(LocalDate.now().minusDays(daysAgo));
        application.setNotes(notes);
        return application;
    }
}