package com.example.tarecruitment.servlet;

import com.example.tarecruitment.service.AdminService;
import com.example.tarecruitment.service.ApplicationService;
import com.example.tarecruitment.service.AuthService;
import com.example.tarecruitment.service.JobService;
import com.example.tarecruitment.service.ProfileService;
import com.example.tarecruitment.service.RegistrationService;
import com.example.tarecruitment.service.UserService;
import com.example.tarecruitment.storage.ApplicationRepository;
import com.example.tarecruitment.storage.JobRepository;
import com.example.tarecruitment.storage.JsonDataManager;
import com.example.tarecruitment.storage.MOProfileRepository;
import com.example.tarecruitment.storage.TAProfileRepository;
import com.example.tarecruitment.storage.UserRepository;
import com.example.tarecruitment.util.AppConstants;
import com.example.tarecruitment.util.AppContainer;
import com.example.tarecruitment.util.DataInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener
public class AppBootstrapListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        Path dataDir = resolveDataDir(servletContext);
        JsonDataManager dataManager = new JsonDataManager();

        UserRepository userRepository = new UserRepository(dataDir.resolve("users.json"), dataManager);
        TAProfileRepository taProfileRepository = new TAProfileRepository(dataDir.resolve("profiles.json"), dataManager);
        MOProfileRepository moProfileRepository = new MOProfileRepository(dataDir.resolve("mo_profiles.json"), dataManager);
        JobRepository jobRepository = new JobRepository(dataDir.resolve("jobs.json"), dataManager);
        ApplicationRepository applicationRepository = new ApplicationRepository(dataDir.resolve("applications.json"), dataManager);

        DataInitializer initializer = new DataInitializer(
                userRepository,
                taProfileRepository,
                moProfileRepository,
                jobRepository,
                applicationRepository
        );
        initializer.initializeIfEmpty();

        AuthService authService = new AuthService(userRepository);
        RegistrationService registrationService = new RegistrationService(userRepository, taProfileRepository, moProfileRepository);
        UserService userService = new UserService(userRepository);
        ProfileService profileService = new ProfileService(taProfileRepository, moProfileRepository);
        JobService jobService = new JobService(jobRepository);
        ApplicationService applicationService = new ApplicationService(applicationRepository, jobRepository);
        AdminService adminService = new AdminService(userRepository, jobRepository, applicationRepository);

        AppContainer container = new AppContainer(
                authService,
                registrationService,
                userService,
                profileService,
                jobService,
                applicationService,
                adminService
        );
        servletContext.setAttribute(AppConstants.ATTR_CONTAINER, container);
    }

    private Path resolveDataDir(ServletContext servletContext) {
        String configuredDataDir = System.getProperty("tarecruitment.dataDir");
        if (configuredDataDir != null && !configuredDataDir.isBlank()) {
            return Paths.get(configuredDataDir.trim());
        }

        String contextParam = servletContext.getInitParameter("dataDir");
        if (contextParam != null && !contextParam.isBlank()) {
            return Paths.get(contextParam.trim());
        }

        return Paths.get("data");
    }
}
