package com.example.tarecruitment.service;

import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.storage.JobRepository;
import com.example.tarecruitment.storage.JsonDataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JobServiceTest {
    private JobService jobService;
    private JobRepository jobRepository;

    @BeforeEach
    void setUp() throws Exception {
        Path tempDir = Files.createTempDirectory("ta-recruitment-job-test");
        JsonDataManager dataManager = new JsonDataManager();
        jobRepository = new JobRepository(tempDir.resolve("jobs-test.json"), dataManager);
        jobService = new JobService(jobRepository);

        // 初始化测试数据
        Job testJob = new Job("J001", "Python TA", "Lab support", List.of("Python"), 8, "", "Remote", "U101", JobStatus.OPEN, "2026-04-01T10:00:00");
        jobRepository.add(testJob);
    }

    @Test
    void postJob_ShouldCreateValidJob() {
        Job newJob = jobService.postJob(
                "Java TA",
                "Teach Java lab",
                "Java,Spring",
                6,
                "2026-05-01",
                "Onsite",
                "U102"
        );

        assertNotNull(newJob.getId());
        assertEquals("Java TA", newJob.getTitle());
        assertEquals(List.of("Java", "Spring"), newJob.getRequiredSkills());
        assertEquals(6, newJob.getHoursPerWeek());
        assertEquals(JobStatus.OPEN, newJob.getStatus());
        assertEquals(2, jobRepository.findAll().size());
    }

    @Test
    void postJob_InvalidHours_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                jobService.postJob(
                        "Java TA",
                        "Teach Java lab",
                        "Java",
                        0,
                        "U102"
                )
        );
    }

    @Test
    void searchOpenJobs_ByLocationMode_ShouldFilter() {
        List<Job> remoteJobs = jobService.searchOpenJobs("", "Remote");
        assertEquals(1, remoteJobs.size());
        assertEquals("J001", remoteJobs.get(0).getId());

        List<Job> onsiteJobs = jobService.searchOpenJobs("", "Onsite");
        assertEquals(0, onsiteJobs.size());
    }

    @Test
    void searchOpenJobs_ByQuery_ShouldMatchSkills() {
        List<Job> pythonJobs = jobService.searchOpenJobs("Python", "");
        assertEquals(1, pythonJobs.size());

        List<Job> javaJobs = jobService.searchOpenJobs("Java", "");
        assertEquals(0, javaJobs.size());
    }

    @Test
    void updateJobStatus_ShouldChangeStatus() {
        jobService.updateJobStatus("J001", JobStatus.CLOSED);
        Optional<Job> updatedJob = jobRepository.findById("J001");
        assertTrue(updatedJob.isPresent());
        assertEquals(JobStatus.CLOSED, updatedJob.get().getStatus());
    }

    @Test
    void updateJobStatus_InvalidJobId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                jobService.updateJobStatus("J999", JobStatus.CLOSED)
        );
    }
}