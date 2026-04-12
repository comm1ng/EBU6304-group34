package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "manageApplicantsServlet", urlPatterns = {"/mo/manage-applicants"})
public class ManageApplicantsServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.MO);
        if (user == null) {
            return;
        }

        String jobId = ValidationUtil.safeTrim(request.getParameter("jobId"));
        if (jobId.isBlank()) {
            setFlashError(request, "Please select a job first.");
            redirect(response, request, "/mo/dashboard");
            return;
        }

        Job job = container().getJobService().getById(jobId).orElse(null);
        if (job == null || !user.getId().equals(job.getPostedByUserId())) {
            setFlashError(request, "Job not found or access denied.");
            redirect(response, request, "/mo/dashboard");
            return;
        }

        List<JobApplication> applications = container().getApplicationService().getApplicationsByJob(jobId);
        Map<String, User> taById = new HashMap<>();
        for (User ta : container().getUserService().getTAUsers()) {
            taById.put(ta.getId(), ta);
        }

        request.setAttribute("job", job);
        request.setAttribute("applications", applications);
        request.setAttribute("taById", taById);

        forward(request, response, "manageApplicants.jsp", user, Role.MO);
    }
}
