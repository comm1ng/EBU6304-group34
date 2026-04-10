package com.example.tarecruitment.storage;

import com.example.tarecruitment.model.Job;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JobRepository {
    private final Path path;
    private final JsonDataManager dataManager;

    public JobRepository(Path path, JsonDataManager dataManager) {
        this.path = path;
        this.dataManager = dataManager;
    }

    public List<Job> findAll() {
        return new ArrayList<>(dataManager.readList(path, Job.class));
    }

    public Optional<Job> findById(String id) {
        return findAll().stream().filter(job -> job.getId().equals(id)).findFirst();
    }

    public List<Job> findByPoster(String postedByUserId) {
        return findAll().stream()
                .filter(job -> job.getPostedByUserId().equals(postedByUserId))
                .collect(Collectors.toList());
    }

    public void saveAll(List<Job> jobs) {
        dataManager.writeList(path, jobs);
    }

    public void add(Job job) {
        List<Job> jobs = findAll();
        jobs.add(job);
        saveAll(jobs);
    }

    public void update(Job updatedJob) {
        List<Job> jobs = findAll();
        for (int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).getId().equals(updatedJob.getId())) {
                jobs.set(i, updatedJob);
                saveAll(jobs);
                return;
            }
        }
        throw new IllegalArgumentException("Job not found: " + updatedJob.getId());
    }
}
