package com.example.tarecruitment.storage;

import com.example.tarecruitment.model.JobApplication;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApplicationRepository {
    private final Path path;
    private final JsonDataManager dataManager;

    public ApplicationRepository(Path path, JsonDataManager dataManager) {
        this.path = path;
        this.dataManager = dataManager;
    }

    public List<JobApplication> findAll() {
        return new ArrayList<>(dataManager.readList(path, JobApplication.class));
    }

    public Optional<JobApplication> findById(String id) {
        return findAll().stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public List<JobApplication> findByTaUserId(String taUserId) {
        return findAll().stream()
                .filter(app -> app.getTaUserId().equals(taUserId))
                .collect(Collectors.toList());
    }

    public List<JobApplication> findByJobId(String jobId) {
        return findAll().stream()
                .filter(app -> app.getJobId().equals(jobId))
                .collect(Collectors.toList());
    }

    public Optional<JobApplication> findByJobIdAndTaUserId(String jobId, String taUserId) {
        return findAll().stream()
                .filter(app -> app.getJobId().equals(jobId) && app.getTaUserId().equals(taUserId))
                .findFirst();
    }

    public void saveAll(List<JobApplication> applications) {
        dataManager.writeList(path, applications);
    }

    public void add(JobApplication application) {
        List<JobApplication> applications = findAll();
        applications.add(application);
        saveAll(applications);
    }

    public void update(JobApplication updatedApplication) {
        List<JobApplication> applications = findAll();
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).getId().equals(updatedApplication.getId())) {
                applications.set(i, updatedApplication);
                saveAll(applications);
                return;
            }
        }
        throw new IllegalArgumentException("Application not found: " + updatedApplication.getId());
    }
}
