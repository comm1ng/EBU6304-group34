package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.SystemStats;
import com.example.tarecruitment.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "systemOverviewServlet", urlPatterns = {"/admin/system-overview"})
public class SystemOverviewServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.ADMIN);
        if (user == null) {
            return;
        }

        SystemStats stats = container().getAdminService().getSystemStats();

        List<Job> latestJobs = container().getJobService().getAllJobs().stream()
                .sorted(Comparator.comparing(Job::getCreatedAt, Comparator.nullsLast(String::compareTo)).reversed())
                .limit(5)
                .collect(Collectors.toList());
        Map<String, Job> jobById = new HashMap<>();
        for (Job job : container().getJobService().getAllJobs()) {
            jobById.put(job.getId(), job);
        }

        Map<String, User> userById = new HashMap<>();
        for (User account : container().getUserService().getAllUsers()) {
            userById.put(account.getId(), account);
        }

        List<JobApplication> latestApplications = container().getApplicationService().getAllApplications().stream()
                .sorted(Comparator.comparing(JobApplication::getAppliedAt, Comparator.nullsLast(String::compareTo)).reversed())
                .limit(8)
                .collect(Collectors.toList());

        request.setAttribute("stats", stats);
        request.setAttribute("latestJobs", latestJobs);
        request.setAttribute("jobById", jobById);
        request.setAttribute("userById", userById);
        request.setAttribute("latestApplications", latestApplications);

        forward(request, response, "systemOverview.jsp", user, Role.ADMIN);
    }
}

