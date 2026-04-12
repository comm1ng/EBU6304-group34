package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "applyJobServlet", urlPatterns = {"/jobs/apply"})
public class ApplyJobServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = requireRole(request, response, Role.TA);
        if (user == null) {
            return;
        }

        String jobId = ValidationUtil.safeTrim(request.getParameter("jobId"));
        try {
            container().getApplicationService().applyForJob(jobId, user.getId());
            setFlashSuccess(request, "Application submitted successfully.");
        } catch (IllegalArgumentException ex) {
            setFlashError(request, ex.getMessage());
        }
        redirect(response, request, "/jobs");
    }
}
