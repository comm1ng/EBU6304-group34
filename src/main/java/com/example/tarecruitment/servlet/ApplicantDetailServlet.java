package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "applicantDetailServlet", urlPatterns = {"/mo/applicant-detail"})
public class ApplicantDetailServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User moUser = requireRole(request, response, Role.MO);
        if (moUser == null) {
            return;
        }

        String jobId = ValidationUtil.safeTrim(request.getParameter("jobId"));
        String taUserId = ValidationUtil.safeTrim(request.getParameter("taUserId"));

        if (jobId.isBlank() || taUserId.isBlank()) {
            setFlashError(request, "Job and applicant are required.");
            redirect(response, request, "/mo/dashboard");
            return;
        }

        Job job = container().getJobService().getById(jobId).orElse(null);
        if (job == null || !moUser.getId().equals(job.getPostedByUserId())) {
            setFlashError(request, "Job not found or access denied.");
            redirect(response, request, "/mo/dashboard");
            return;
        }

        JobApplication application = container().getApplicationService()
                .getApplicationByJobAndTa(jobId, taUserId)
                .orElse(null);
        if (application == null) {
            setFlashError(request, "Application not found for this TA.");
            redirect(response, request, "/mo/manage-applicants?jobId=" + jobId);
            return;
        }

        User applicant = container().getUserService().findById(taUserId).orElse(null);
        if (applicant == null || !applicant.hasRole(Role.TA)) {
            setFlashError(request, "TA applicant not found.");
            redirect(response, request, "/mo/manage-applicants?jobId=" + jobId);
            return;
        }

        TAProfile taProfile = container().getProfileService().getOrCreateTaProfile(taUserId);

        request.setAttribute("job", job);
        request.setAttribute("application", application);
        request.setAttribute("applicant", applicant);
        request.setAttribute("taProfile", taProfile);

        forward(request, response, "applicantDetail.jsp", moUser, Role.MO);
    }
}
