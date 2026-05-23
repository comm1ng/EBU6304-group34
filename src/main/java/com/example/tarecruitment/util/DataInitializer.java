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
                        List.of(Role.TA), List.of("Java", "Communication", "Data Structures")),
                new User("U002", "ta2", "123456", "Ben Liu", "ben.liu@bupt.edu.cn",
                        List.of(Role.TA), List.of("Python", "Data Analysis", "Pandas")),
                new User("U003", "ta3", "123456", "Diana Sun", "diana.sun@bupt.edu.cn",
                        List.of(Role.TA), List.of("Academic Writing", "English", "Feedback")),
                new User("U004", "ta4", "123456", "Eric Zhao", "eric.zhao@bupt.edu.cn",
                        List.of(Role.TA), List.of("Machine Learning", "Python", "Jupyter")),
                new User("U005", "ta5", "123456", "Fiona Chen", "fiona.chen@bupt.edu.cn",
                        List.of(Role.TA), List.of("Web Design", "HTML", "CSS", "Accessibility")),
                new User("U101", "mo1", "123456", "Dr. Li", "li@bupt.edu.cn",
                        List.of(Role.MO), List.of()),
                new User("U102", "mo2", "123456", "Prof. Chen", "chen@bupt.edu.cn",
                        List.of(Role.MO), List.of()),
                new User("U151", "dual1", "123456", "Chris Zhang", "chris@bupt.edu.cn",
                        List.of(Role.TA, Role.MO), List.of("Programming", "Mentoring", "SQL")),
                new User("U999", "admin", "admin123", "System Admin", "admin@bupt.edu.cn",
                        List.of(Role.ADMIN), List.of())
        ));
    }

    private void seedProfiles() {
        if (!profileRepository.findAll().isEmpty()) {
            return;
        }

        profileRepository.saveAll(List.of(
                new TAProfile("U001", "Computer Science", "Year 3", List.of("Java", "Data Structures", "Servlet", "Communication"),
                        "uploads/cv/alice_wang_resume.pdf", "Strong Java lab support background with experience debugging servlet assignments and explaining data structures.",
                        "Helped with programming labs, code walkthroughs, and tutorial Q&A for first-year Java modules."),
                new TAProfile("U002", "Data Science", "Year 2", List.of("Python", "Pandas", "Excel", "Statistics"),
                        "uploads/cv/ben_liu_resume.pdf", "Comfortable supporting introductory analytics modules, spreadsheet exercises, and Python notebooks.",
                        "Facilitated peer-learning sessions for statistics courses and helped classmates clean datasets."),
                new TAProfile("U003", "English and Communication", "Year 4", List.of("Academic Writing", "English", "Feedback", "Presentation"),
                        "uploads/cv/diana_sun_resume.pdf", "Experienced in reviewing essays, giving structured feedback, and coaching presentation delivery.",
                        "Worked as a peer writing mentor and supported international students in citation and argument structure."),
                new TAProfile("U004", "Artificial Intelligence", "Year 3", List.of("Machine Learning", "Python", "Jupyter", "Data Visualization"),
                        "uploads/cv/eric_zhao_resume.pdf", "Built machine learning notebooks and can support practical labs on classification, evaluation, and visualization.",
                        "Led study groups on scikit-learn, model evaluation, and Python data pipelines."),
                new TAProfile("U005", "Digital Media Technology", "Year 2", List.of("Web Design", "HTML", "CSS", "Accessibility", "Figma"),
                        "uploads/cv/fiona_chen_resume.pdf", "Frontend-focused TA candidate with accessibility awareness and experience giving UI feedback.",
                        "Designed responsive coursework prototypes and reviewed classmates' HTML/CSS layouts."),
                new TAProfile("U151", "Software Engineering", "Year 2", List.of("Python", "SQL", "Mentoring", "Programming"),
                        "uploads/cv/chris_zhang_resume.pdf", "Part-time tutor with strong communication skills and database programming experience.",
                        "Assisted first-year coding bootcamps and helped students debug SQL exercises.")
        ));
    }

    private void seedMoProfiles() {
        if (!moProfileRepository.findAll().isEmpty()) {
            return;
        }

        moProfileRepository.saveAll(List.of(
                new MOProfile("U101", "BUPT International School", "Module Organiser", "Manages module staffing."),
                new MOProfile("U102", "BUPT International School", "Senior Lecturer", "Leads digital media modules.")
        ));
    }

    private void seedJobs() {
        if (!jobRepository.findAll().isEmpty()) {
            return;
        }

        jobRepository.saveAll(List.of(
                new Job("J001", "Java Programming TA", "Support Java lab sessions, review servlet assignments, and answer data structure questions.",
                        List.of("Java", "Servlet", "Data Structures", "Communication"), 6, "2026-06-20", "On-campus", "U101", JobStatus.OPEN, "2026-04-01T09:00:00"),
                new Job("J002", "Exam Invigilation Assistant", "Support invigilation, attendance checking, incident reporting, and classroom coordination.",
                        List.of("Responsibility", "Punctuality", "Communication"), 4, "2026-05-30", "On-campus", "U101", JobStatus.OPEN, "2026-04-02T11:00:00"),
                new Job("J003", "Programming Workshop Assistant", "Help organise coding workshop sessions, mentor small groups, and prepare Python exercises.",
                        List.of("Python", "Presentation", "Mentoring", "Communication"), 5, "2026-06-10", "Hybrid", "U151", JobStatus.OPEN, "2026-04-03T14:00:00"),
                new Job("J004", "Academic Writing Support TA", "Assist students in academic writing clinics, citation practice, essay planning, and feedback sessions.",
                        List.of("Academic Writing", "English", "Feedback", "Presentation"), 3, "2026-06-05", "Online", "U102", JobStatus.OPEN, "2026-03-28T10:30:00"),
                new Job("J005", "Machine Learning Lab Demonstrator", "Support practical labs on supervised learning, scikit-learn notebooks, model evaluation, and visualization.",
                        List.of("Machine Learning", "Python", "Jupyter", "Data Visualization"), 7, "2026-06-18", "Hybrid", "U101", JobStatus.OPEN, "2026-04-08T15:20:00"),
                new Job("J006", "Web Design Studio Assistant", "Help students improve HTML/CSS coursework, responsive layouts, accessibility checks, and UI critique.",
                        List.of("Web Design", "HTML", "CSS", "Accessibility"), 5, "2026-06-15", "On-campus", "U102", JobStatus.OPEN, "2026-04-09T13:10:00"),
                new Job("J007", "Database Tutorial Assistant", "Support SQL tutorials, ER modelling practice, relational algebra review, and debugging database coursework.",
                        List.of("SQL", "Database", "Programming", "Mentoring"), 6, "2026-06-22", "Online", "U151", JobStatus.OPEN, "2026-04-10T09:45:00")
        ));
    }

    private void seedApplications() {
        if (!applicationRepository.findAll().isEmpty()) {
            return;
        }

        applicationRepository.saveAll(List.of(
                new JobApplication("A001", "J001", "U001", ApplicationStatus.PENDING,
                        "2026-04-01T10:00:00", null, null),
                new JobApplication("A002", "J002", "U001", ApplicationStatus.ACCEPTED,
                        "2026-04-02T13:20:00", "2026-04-03T09:00:00", "U101"),
                new JobApplication("A003", "J003", "U151", ApplicationStatus.PENDING,
                        "2026-04-04T16:10:00", null, null),
                new JobApplication("A004", "J004", "U003", ApplicationStatus.PENDING,
                        "2026-04-05T12:00:00", null, null),
                new JobApplication("A005", "J005", "U004", ApplicationStatus.PENDING,
                        "2026-04-06T09:15:00", null, null),
                new JobApplication("A006", "J006", "U005", ApplicationStatus.PENDING,
                        "2026-04-06T15:30:00", null, null),
                new JobApplication("A007", "J007", "U151", ApplicationStatus.PENDING,
                        "2026-04-07T11:40:00", null, null),
                new JobApplication("A008", "J001", "U004", ApplicationStatus.PENDING,
                        "2026-04-08T10:25:00", null, null),
                new JobApplication("A009", "J005", "U002", ApplicationStatus.PENDING,
                        "2026-04-08T14:00:00", null, null),
                new JobApplication("A010", "J006", "U003", ApplicationStatus.REJECTED,
                        "2026-04-09T13:20:00", "2026-04-10T16:00:00", "U102"),
                new JobApplication("A011", "J004", "U005", ApplicationStatus.PENDING,
                        "2026-04-10T10:05:00", null, null),
                new JobApplication("A012", "J003", "U002", ApplicationStatus.PENDING,
                        "2026-04-11T17:10:00", null, null),
                new JobApplication("A013", "J007", "U001", ApplicationStatus.PENDING,
                        "2026-04-12T09:00:00", null, null)
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
