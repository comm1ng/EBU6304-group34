package com.example.tarecruitment.util;

import com.example.tarecruitment.service.AdminService;
import com.example.tarecruitment.service.ApplicationService;
import com.example.tarecruitment.service.AuthService;
import com.example.tarecruitment.service.JobService;
import com.example.tarecruitment.service.ProfileService;
import com.example.tarecruitment.service.RegistrationService;
import com.example.tarecruitment.service.UserService;

public class AppContainer {
    private final AuthService authService;
    private final RegistrationService registrationService;
    private final UserService userService;
    private final ProfileService profileService;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final AdminService adminService;

    public AppContainer(AuthService authService,
                        RegistrationService registrationService,
                        UserService userService,
                        ProfileService profileService,
                        JobService jobService,
                        ApplicationService applicationService,
                        AdminService adminService) {
        this.authService = authService;
        this.registrationService = registrationService;
        this.userService = userService;
        this.profileService = profileService;
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.adminService = adminService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public UserService getUserService() {
        return userService;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public JobService getJobService() {
        return jobService;
    }

    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public AdminService getAdminService() {
        return adminService;
    }
}
