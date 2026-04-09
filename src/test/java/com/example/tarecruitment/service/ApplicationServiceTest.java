package com.example.tarecruitment.service;

import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.storage.ApplicationRepository;
import com.example.tarecruitment.storage.JobRepository;
import com.example.tarecruitment.storage.JsonDataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationServiceTest {
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() throws Exception {
        Path tempDir = Files.createTempDirectory("ta-recruitment-app-test");
        JsonDataManager dataManager = new JsonDataManager();

        JobRepository jobRepository = new JobRepository(tempDir.resolve("jobs.json"), dataManager);
        ApplicationRepository applicationRepository = new ApplicationRepository(tempDir.resolve("applications.json"), dataManager);

        jobRepository.saveAll(List.of(
                new Job("J001", "Java TA", "Lab support", List.of("Java"), 6, "U101", JobStatus.OPEN, "2026-04-01T10:00:00")
        ));

        applicationService = new ApplicationService(applicationRepository, jobRepository);
    }

    @Test
    void applyForJobShouldCreatePendingApplication() {
        JobApplication app = applicationService.applyForJob("J001", "U001");

        assertEquals("J001", app.getJobId());
        assertEquals("U001", app.getTaUserId());
        assertEquals(ApplicationStatus.PENDING, app.getStatus());
        assertEquals(1, applicationService.getAllApplications().size());
    }

    @Test
    void applyForJobShouldRejectDuplicateApplication() {
        applicationService.applyForJob("J001", "U001");

        assertThrows(IllegalArgumentException.class,
                () -> applicationService.applyForJob("J001", "U001"));
    }
}
