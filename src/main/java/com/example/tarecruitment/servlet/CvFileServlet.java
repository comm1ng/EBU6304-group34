package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(name = "cvFileServlet", urlPatterns = {"/cv-file"})
public class CvFileServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = requireLogin(request, response);
        if (currentUser == null) {
            return;
        }

        String taUserId = ValidationUtil.safeTrim(request.getParameter("taUserId"));
        String jobId = ValidationUtil.safeTrim(request.getParameter("jobId"));

        if (taUserId.isBlank()) {
            taUserId = currentUser.getId();
        }

        if (!hasPermissionToViewCv(currentUser, taUserId, jobId)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied to this CV file.");
            return;
        }

        TAProfile taProfile = container().getProfileService().getOrCreateTaProfile(taUserId);
        String cvFilePath = ValidationUtil.safeTrim(taProfile.getCvFilePath());
        if (cvFilePath.isBlank()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "CV file not found.");
            return;
        }

        Path dataDir = resolveDataDir().toAbsolutePath().normalize();
        Path cvFile = dataDir.resolve(cvFilePath).normalize();

        if (!cvFile.startsWith(dataDir)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CV path.");
            return;
        }

        if (!Files.exists(cvFile) || !Files.isRegularFile(cvFile)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "CV file does not exist on server.");
            return;
        }

        String contentType = getServletContext().getMimeType(cvFile.getFileName().toString());
        if (contentType == null || contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + cvFile.getFileName() + "\"");
        response.setContentLengthLong(Files.size(cvFile));

        try (OutputStream outputStream = response.getOutputStream()) {
            Files.copy(cvFile, outputStream);
            outputStream.flush();
        }
    }

    private boolean hasPermissionToViewCv(User currentUser, String taUserId, String jobId) {
        if (currentUser.hasRole(Role.ADMIN)) {
            return true;
        }

        if (currentUser.hasRole(Role.TA) && currentUser.getId().equals(taUserId)) {
            return true;
        }

        if (currentUser.hasRole(Role.MO)) {
            if (jobId.isBlank()) {
                return false;
            }

            Job job = container().getJobService().getById(jobId).orElse(null);
            if (job == null || !currentUser.getId().equals(job.getPostedByUserId())) {
                return false;
            }

            return container().getApplicationService().getApplicationByJobAndTa(jobId, taUserId).isPresent();
        }

        return false;
    }
}
