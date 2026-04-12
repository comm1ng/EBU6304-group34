package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "manageJobsServlet", urlPatterns = {"/mo/manage-jobs"})
public class ManageJobsServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.MO);
        if (user == null) {
            return;
        }

        List<Job> myJobs = container().getJobService().getJobsByPoster(user.getId());
        List<JobApplication> allApplications = container().getApplicationService().getAllApplications();

        Map<String, Integer> applicantCountByJobId = new HashMap<>();
        for (JobApplication application : allApplications) {
            applicantCountByJobId.put(
                    application.getJobId(),
                    applicantCountByJobId.getOrDefault(application.getJobId(), 0) + 1
            );
        }

        request.setAttribute("myJobs", myJobs);
        request.setAttribute("applicantCountByJobId", applicantCountByJobId);

        forward(request, response, "manageJobs.jsp", user, Role.MO);
    }
}

