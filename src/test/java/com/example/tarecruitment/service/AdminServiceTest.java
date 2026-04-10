package com.example.tarecruitment.service;

import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.SystemStats;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.model.WorkloadSummary;
import com.example.tarecruitment.storage.ApplicationRepository;
import com.example.tarecruitment.storage.JobRepository;
import com.example.tarecruitment.storage.JsonDataManager;
import com.example.tarecruitment.storage.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminServiceTest {
    private AdminService adminService;

    @BeforeEach
    void setUp() throws Exception {
        Path tempDir = Files.createTempDirectory("ta-recruitment-admin-test");
        JsonDataManager dataManager = new JsonDataManager();

        UserRepository userRepository = new UserRepository(tempDir.resolve("users.json"), dataManager);
        JobRepository jobRepository = new JobRepository(tempDir.resolve("jobs.json"), dataManager);
        ApplicationRepository applicationRepository = new ApplicationRepository(tempDir.resolve("applications.json"), dataManager);

        userRepository.saveAll(List.of(
                new User("U001", "ta1", "123456", "Alice", "alice@bupt.edu.cn", List.of(Role.TA), List.of()),
                new User("U101", "mo1", "123456", "Dr. Li", "li@bupt.edu.cn", List.of(Role.MO), List.of())
        ));

        jobRepository.saveAll(List.of(
                new Job("J001", "Java TA", "Lab support", List.of("Java"), 6, "U101", JobStatus.OPEN, "2026-04-01T10:00:00"),
                new Job("J002", "Invigilation", "Exam support", List.of("Punctuality"), 4, "U101", JobStatus.OPEN, "2026-04-02T10:00:00")
        ));

        applicationRepository.saveAll(List.of(
                new JobApplication("A001", "J001", "U001", ApplicationStatus.ACCEPTED, "2026-04-01T10:00:00", "2026-04-02T10:00:00", "U101"),
                new JobApplication("A002", "J002", "U001", ApplicationStatus.PENDING, "2026-04-02T10:00:00", null, null)
        ));

        adminService = new AdminService(userRepository, jobRepository, applicationRepository);
    }

    @Test
    void workloadSummaryShouldAggregateAcceptedAndPending() {
        List<WorkloadSummary> summaries = adminService.getWorkloadSummary();

        assertEquals(1, summaries.size());
        WorkloadSummary summary = summaries.get(0);

        assertEquals("U001", summary.getTaUserId());
        assertEquals(1, summary.getAcceptedJobs());
        assertEquals(6, summary.getTotalHoursPerWeek());
        assertEquals(1, summary.getPendingApplications());
    }

    @Test
    void systemStatsShouldIncludeOpenJobsAndAcceptedApplications() {
        SystemStats stats = adminService.getSystemStats();

        assertEquals(2, stats.getTotalUsers());
        assertEquals(1, stats.getTotalTaUsers());
        assertEquals(1, stats.getTotalMoUsers());
        assertEquals(2, stats.getTotalJobs());
        assertEquals(2, stats.getOpenJobs());
        assertEquals(2, stats.getTotalApplications());
        assertEquals(1, stats.getAcceptedApplications());
    }
}
