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

@WebServlet(name = "myApplicationsServlet", urlPatterns = {"/my-applications"})
public class MyApplicationsServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.TA);
        if (user == null) {
            return;
        }

        List<JobApplication> myApplications = container().getApplicationService().getApplicationsByTa(user.getId());
        List<Job> allJobs = container().getJobService().getAllJobs();

        Map<String, Job> jobById = new HashMap<>();
        for (Job job : allJobs) {
            jobById.put(job.getId(), job);
        }

        request.setAttribute("myApplications", myApplications);
        request.setAttribute("jobById", jobById);
        forward(request, response, "myApplications.jsp", user, Role.TA);
    }
}
