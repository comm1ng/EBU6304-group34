package com.example.tarecruitment.service;

import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.SystemStats;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.model.WorkloadSummary;
import com.example.tarecruitment.storage.ApplicationRepository;
import com.example.tarecruitment.storage.JobRepository;
import com.example.tarecruitment.storage.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdminService {
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    public AdminService(UserRepository userRepository, JobRepository jobRepository, ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<WorkloadSummary> getWorkloadSummary() {
        List<User> taUsers = userRepository.findAll().stream()
                .filter(user -> user.hasRole(Role.TA))
                .collect(Collectors.toList());

        Map<String, Job> jobsById = jobRepository.findAll().stream()
                .collect(Collectors.toMap(Job::getId, Function.identity()));

        List<JobApplication> applications = applicationRepository.findAll();
        List<WorkloadSummary> result = new ArrayList<>();

        for (User ta : taUsers) {
            int pendingCount = 0;
            int acceptedCount = 0;
            int totalHours = 0;

            for (JobApplication application : applications) {
                if (!application.getTaUserId().equals(ta.getId())) {
                    continue;
                }

                if (application.getStatus() == ApplicationStatus.PENDING) {
                    pendingCount++;
                }

                if (application.getStatus() == ApplicationStatus.ACCEPTED) {
                    acceptedCount++;
                    Job job = jobsById.get(application.getJobId());
                    if (job != null) {
                        totalHours += job.getHoursPerWeek();
                    }
                }
            }

            result.add(new WorkloadSummary(
                    ta.getId(),
                    ta.getFullName(),
                    acceptedCount,
                    totalHours,
                    pendingCount
            ));
        }

        result.sort(Comparator.comparingInt(WorkloadSummary::getTotalHoursPerWeek).reversed());
        return result;
    }

    public SystemStats getSystemStats() {
        List<User> users = userRepository.findAll();
        return new SystemStats(
                users.size(),
                (int) users.stream().filter(user -> user.hasRole(Role.TA)).count(),
                (int) users.stream().filter(user -> user.hasRole(Role.MO)).count(),
                (int) users.stream().filter(user -> user.hasRole(Role.ADMIN)).count(),
                jobRepository.findAll().size(),
                applicationRepository.findAll().size()
        );
    }
}
