package com.example.tarecruitment.service;

import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.storage.JobRepository;
import com.example.tarecruitment.util.CsvUtil;
import com.example.tarecruitment.util.IdGenerator;
import com.example.tarecruitment.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job postJob(String title, String description, String requiredSkillsCsv, int hoursPerWeek, String postedByUserId) {
        ValidationUtil.requireNotBlank(postedByUserId, "Posted by user");
        ValidationUtil.requireNotBlank(title, "Job title");
        ValidationUtil.requireNotBlank(description, "Job description");
        if (hoursPerWeek <= 0) {
            throw new IllegalArgumentException("Hours per week must be greater than 0.");
        }

        List<Job> jobs = jobRepository.findAll();
        String nextId = IdGenerator.nextId("J", jobs.stream().map(Job::getId).collect(Collectors.toList()));

        Job job = new Job(nextId, title.trim(), description.trim(), CsvUtil.parseCommaSeparated(requiredSkillsCsv),
                hoursPerWeek, postedByUserId, JobStatus.OPEN, LocalDateTime.now().toString());

        jobRepository.add(job);
        return job;
    }

    public List<Job> getOpenJobs() {
        return jobRepository.findAll().stream()
                .filter(job -> job.getStatus() == JobStatus.OPEN)
                .collect(Collectors.toList());
    }

    public List<Job> getJobsByPoster(String posterUserId) {
        return jobRepository.findByPoster(posterUserId);
    }

    public Optional<Job> getById(String jobId) {
        return jobRepository.findById(jobId);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public void updateJobStatus(String jobId, JobStatus status) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found."));

        job.setStatus(status);
        jobRepository.update(job);
    }
}
