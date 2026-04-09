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

@WebServlet(name = "jobsServlet", urlPatterns = {"/jobs"})
public class JobsServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.TA);
        if (user == null) {
            return;
        }

        List<Job> openJobs = container().getJobService().getOpenJobs();
        List<JobApplication> myApplications = container().getApplicationService().getApplicationsByTa(user.getId());

        Map<String, String> appliedStatusByJobId = new HashMap<>();
        for (JobApplication application : myApplications) {
            appliedStatusByJobId.put(application.getJobId(), application.getStatus().name());
        }

        request.setAttribute("openJobs", openJobs);
        request.setAttribute("appliedStatusByJobId", appliedStatusByJobId);
        forward(request, response, "jobs.jsp", user, Role.TA);
    }
}
