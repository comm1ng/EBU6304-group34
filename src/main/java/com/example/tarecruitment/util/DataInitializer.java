package com.example.tarecruitment.util;

import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.model.MOProfile;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.storage.ApplicationRepository;
import com.example.tarecruitment.storage.JobRepository;
import com.example.tarecruitment.storage.MOProfileRepository;
import com.example.tarecruitment.storage.TAProfileRepository;
import com.example.tarecruitment.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class DataInitializer {
    private final UserRepository userRepository;
    private final TAProfileRepository profileRepository;
    private final MOProfileRepository moProfileRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    public DataInitializer(UserRepository userRepository,
                           TAProfileRepository profileRepository,
                           MOProfileRepository moProfileRepository,
                           JobRepository jobRepository,
                           ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.moProfileRepository = moProfileRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    private void seedUsers() {
        if (!userRepository.findAll().isEmpty()) {
            return;
        }

        userRepository.saveAll(List.of(
                new User("U001", "ta1", "123456", "Alice Wang", "alice@bupt.edu.cn",
                        List.of(Role.TA), List.of("Java", "Communication")),
                new User("U101", "mo1", "123456", "Dr. Li", "li@bupt.edu.cn",
                        List.of(Role.MO), List.of()),
                new User("U151", "dual1", "123456", "Chris Zhang", "chris@bupt.edu.cn",
                        List.of(Role.TA, Role.MO), List.of("Programming", "Mentoring")),
                new User("U999", "admin", "admin123", "System Admin", "admin@bupt.edu.cn",
                        List.of(Role.ADMIN), List.of())
        ));
    }

    private void seedProfiles() {
        if (!profileRepository.findAll().isEmpty()) {
            return;
        }

        profileRepository.saveAll(List.of(
                new TAProfile("U001", "Computer Science", "Year 3", List.of("Java", "Data Structures"),
                        "docs/cv/alice.pdf", "Strong Java lab support background.",
                        "Helped with programming labs and tutorials."),
                new TAProfile("U151", "Software Engineering", "Year 2", List.of("Python", "SQL"),
                        "", "Part-time tutor with strong communication skills.",
                        "Assisted first-year coding bootcamps.")
        ));
    }

    private void seedMoProfiles() {
        if (!moProfileRepository.findAll().isEmpty()) {
            return;
        }

        moProfileRepository.saveAll(List.of(
                new MOProfile("U101", "BUPT International School", "Module Organiser", "Manages module staffing."),
                new MOProfile("U151", "BUPT International School", "Activity Coordinator", "Coordinates activities and workshops.")
        ));
    }

    private void seedJobs() {
        if (!jobRepository.findAll().isEmpty()) {
            return;
        }

        jobRepository.saveAll(List.of(
                new Job("J001", "Java Programming TA", "Support Java lab sessions and answer student questions.",
                        List.of("Java", "Communication"), 6, "U101", JobStatus.OPEN, "2026-04-01T09:00:00"),
                new Job("J002", "Exam Invigilation Assistant", "Support invigilation and attendance checking.",
                        List.of("Responsibility", "Punctuality"), 4, "U101", JobStatus.OPEN, "2026-04-02T11:00:00"),
                new Job("J003", "Programming Workshop Assistant", "Help organise coding workshop sessions.",
                        List.of("Python", "Presentation"), 5, "U151", JobStatus.OPEN, "2026-04-03T14:00:00")
        ));
    }

    private void seedApplications() {
        if (!applicationRepository.findAll().isEmpty()) {
            return;
        }

        applicationRepository.saveAll(List.of(
                new JobApplication("A001", "J001", "U001", ApplicationStatus.PENDING,
                        LocalDateTime.now().minusDays(1).toString(), null, null),
                new JobApplication("A002", "J002", "U001", ApplicationStatus.ACCEPTED,
                        LocalDateTime.now().minusDays(2).toString(), LocalDateTime.now().minusDays(1).toString(), "U101"),
                new JobApplication("A003", "J003", "U151", ApplicationStatus.PENDING,
                        LocalDateTime.now().minusHours(10).toString(), null, null)
        ));
    }

    public void initializeIfEmpty() {
        seedUsers();
        seedProfiles();
        seedMoProfiles();
        seedJobs();
        seedApplications();
    }
}
