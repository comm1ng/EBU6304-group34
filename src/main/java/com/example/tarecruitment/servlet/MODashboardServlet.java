package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "moDashboardServlet", urlPatterns = {"/mo/dashboard"})
public class MODashboardServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.MO);
        if (user == null) {
            return;
        }

        List<Job> myJobs = container().getJobService().getJobsByPoster(user.getId());
        Set<String> myJobIds = new HashSet<>();
        for (Job job : myJobs) {
            myJobIds.add(job.getId());
        }

        List<JobApplication> allApplications = container().getApplicationService().getAllApplications();
        long pendingApplicants = allApplications.stream()
                .filter(application -> myJobIds.contains(application.getJobId()))
                .filter(application -> application.getStatus() == ApplicationStatus.PENDING)
                .count();

        long openJobs = myJobs.stream().filter(job -> job.getStatus() == JobStatus.OPEN).count();

        request.setAttribute("myJobs", myJobs);
        request.setAttribute("myJobsCount", myJobs.size());
        request.setAttribute("openJobsCount", openJobs);
        request.setAttribute("pendingApplicants", pendingApplicants);

        forward(request, response, "moDashboard.jsp", user, Role.MO);
    }
}
