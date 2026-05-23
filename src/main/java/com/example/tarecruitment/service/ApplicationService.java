package com.example.tarecruitment.service;

import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.storage.ApplicationRepository;
import com.example.tarecruitment.storage.JobRepository;
import com.example.tarecruitment.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    public JobApplication applyForJob(String jobId, String taUserId) {
        if (jobId == null || jobId.isBlank()) {
            throw new IllegalArgumentException("Job is required.");
        }
        if (taUserId == null || taUserId.isBlank()) {
            throw new IllegalArgumentException("TA user is required.");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Selected job does not exist."));

        if (job.getStatus() != JobStatus.OPEN) {
            throw new IllegalArgumentException("This job is not open for applications.");
        }

        if (applicationRepository.findByJobIdAndTaUserId(jobId, taUserId).isPresent()) {
            throw new IllegalArgumentException("You have already applied for this job.");
        }

        List<JobApplication> all = applicationRepository.findAll();
        String nextId = IdGenerator.nextId("A", all.stream().map(JobApplication::getId).collect(Collectors.toList()));

        JobApplication application = new JobApplication(
                nextId,
                jobId,
                taUserId,
                ApplicationStatus.PENDING,
                LocalDateTime.now().toString(),
                null,
                null
        );

        applicationRepository.add(application);
        return application;
    }

    public List<JobApplication> getApplicationsByTa(String taUserId) {
        return applicationRepository.findByTaUserId(taUserId);
    }

    public List<JobApplication> getApplicationsByJob(String jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    public Optional<JobApplication> getApplicationByJobAndTa(String jobId, String taUserId) {
        return applicationRepository.findByJobIdAndTaUserId(jobId, taUserId);
    }

    public List<JobApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    public void updateStatusByMo(String applicationId, ApplicationStatus status, String moUserId) {
        if (status == null) {
            throw new IllegalArgumentException("Status is required.");
        }
        if (status != ApplicationStatus.PENDING && status != ApplicationStatus.ACCEPTED && status != ApplicationStatus.REJECTED) {
            throw new IllegalArgumentException("Unsupported status.");
        }

        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found."));

        Job job = jobRepository.findById(application.getJobId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found."));

        if (!job.getPostedByUserId().equals(moUserId)) {
            throw new IllegalArgumentException("You can only update applications for your own jobs.");
        }

        application.setStatus(status);
        application.setReviewedAt(LocalDateTime.now().toString());
        application.setReviewedByUserId(moUserId);
        applicationRepository.update(application);
    }
}
