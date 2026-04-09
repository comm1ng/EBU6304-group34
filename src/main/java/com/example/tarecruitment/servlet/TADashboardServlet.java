package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "taDashboardServlet", urlPatterns = {"/ta/dashboard"})
public class TADashboardServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.TA);
        if (user == null) {
            return;
        }

        List<Job> openJobs = container().getJobService().getOpenJobs();
        List<JobApplication> myApplications = container().getApplicationService().getApplicationsByTa(user.getId());

        long pendingCount = myApplications.stream().filter(a -> a.getStatus() == ApplicationStatus.PENDING).count();
        long acceptedCount = myApplications.stream().filter(a -> a.getStatus() == ApplicationStatus.ACCEPTED).count();
        long rejectedCount = myApplications.stream().filter(a -> a.getStatus() == ApplicationStatus.REJECTED).count();

        List<JobApplication> latestApplications = myApplications.stream().limit(5).collect(Collectors.toList());

        request.setAttribute("openJobsCount", openJobs.size());
        request.setAttribute("myApplicationsCount", myApplications.size());
        request.setAttribute("pendingCount", pendingCount);
        request.setAttribute("acceptedCount", acceptedCount);
        request.setAttribute("rejectedCount", rejectedCount);
        request.setAttribute("latestApplications", latestApplications);

        forward(request, response, "taDashboard.jsp", user, Role.TA);
    }
}
