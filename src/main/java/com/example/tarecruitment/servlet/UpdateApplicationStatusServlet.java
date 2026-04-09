package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "updateApplicationStatusServlet", urlPatterns = {"/mo/update-application-status"})
public class UpdateApplicationStatusServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = requireRole(request, response, Role.MO);
        if (user == null) {
            return;
        }

        String applicationId = ValidationUtil.safeTrim(request.getParameter("applicationId"));
        String statusText = ValidationUtil.safeTrim(request.getParameter("status"));
        String jobId = ValidationUtil.safeTrim(request.getParameter("jobId"));

        try {
            ApplicationStatus status = ApplicationStatus.valueOf(statusText);
            container().getApplicationService().updateStatusByMo(applicationId, status, user.getId());
            setFlashSuccess(request, "Application status updated.");
        } catch (IllegalArgumentException ex) {
            setFlashError(request, ex.getMessage());
        }

        if (jobId.isBlank()) {
            redirect(response, request, "/mo/dashboard");
            return;
        }
        redirect(response, request, "/mo/manage-applicants?jobId=" + jobId);
    }
}
