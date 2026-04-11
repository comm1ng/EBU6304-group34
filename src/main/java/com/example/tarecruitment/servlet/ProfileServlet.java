package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.MOProfile;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.CsvUtil;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

@WebServlet(name = "profileServlet", urlPatterns = {"/profile"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 12 * 1024 * 1024
)
public class ProfileServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.TA, Role.MO);
        if (user == null) {
            return;
        }

        Role activeRole = getActiveRole(request);
        bindProfileByRole(request, user, activeRole);
        forward(request, response, "profile.jsp", user, activeRole);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.TA, Role.MO);
        if (user == null) {
            return;
        }

        Role activeRole = getActiveRole(request);
        try {
            String fullName = ValidationUtil.safeTrim(request.getParameter("fullName"));
            String email = ValidationUtil.safeTrim(request.getParameter("email"));
            container().getUserService().updateBasicInfo(user.getId(), fullName, email);

            if (activeRole == Role.TA) {
                TAProfile profile = container().getProfileService().getOrCreateTaProfile(user.getId());
                profile.setMajor(ValidationUtil.safeTrim(request.getParameter("major")));
                profile.setAcademicYear(ValidationUtil.safeTrim(request.getParameter("academicYear")));
                profile.setSkills(CsvUtil.parseCommaSeparated(request.getParameter("skills")));

                String uploadedCvPath = handleCvUpload(request, user.getId());
                if (uploadedCvPath != null) {
                    profile.setCvFilePath(uploadedCvPath);
                }

                profile.setCvSummary(ValidationUtil.safeTrim(request.getParameter("cvSummary")));
                profile.setExperience(ValidationUtil.safeTrim(request.getParameter("experience")));
                container().getProfileService().saveTaProfile(profile);
            } else if (activeRole == Role.MO) {
                MOProfile profile = container().getProfileService().getOrCreateMoProfile(user.getId());
                profile.setWorkUnit(ValidationUtil.safeTrim(request.getParameter("workUnit")));
                profile.setTitle(ValidationUtil.safeTrim(request.getParameter("title")));
                profile.setBio(ValidationUtil.safeTrim(request.getParameter("bio")));
                container().getProfileService().saveMoProfile(profile);
            }

            setFlashSuccess(request, "Profile updated successfully.");
            redirect(response, request, "/profile");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            bindProfileByRole(request, user, activeRole);
            forward(request, response, "profile.jsp", user, activeRole);
        }
    }

    private void bindProfileByRole(HttpServletRequest request, User user, Role role) {
        if (role == Role.TA) {
            request.setAttribute("taProfile", container().getProfileService().getOrCreateTaProfile(user.getId()));
            return;
        }
        if (role == Role.MO) {
            request.setAttribute("moProfile", container().getProfileService().getOrCreateMoProfile(user.getId()));
        }
    }

    private String handleCvUpload(HttpServletRequest request, String userId) {
        try {
            Part cvFilePart = request.getPart("cvFile");
            return saveCvFile(cvFilePart, userId);
        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("CV file exceeds the 10MB upload limit.");
        } catch (ServletException | IOException ex) {
            throw new IllegalArgumentException("Failed to upload CV file.");
        }
    }

    private String saveCvFile(Part cvFilePart, String userId) throws IOException {
        if (cvFilePart == null || cvFilePart.getSize() <= 0) {
            return null;
        }

        String originalName = sanitizeFileName(cvFilePart.getSubmittedFileName());
        if (originalName.isBlank()) {
            originalName = "resume.pdf";
        }

        String lower = originalName.toLowerCase(Locale.ROOT);
        if (!(lower.endsWith(".pdf") || lower.endsWith(".doc") || lower.endsWith(".docx"))) {
            throw new IllegalArgumentException("CV must be a PDF, DOC, or DOCX file.");
        }

        Path uploadDir = resolveDataDir().resolve("uploads").resolve("cv").toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);

        String storedFileName = userId + "_" + System.currentTimeMillis() + "_" + originalName;
        Path target = uploadDir.resolve(storedFileName).normalize();
        if (!target.startsWith(uploadDir)) {
            throw new IllegalArgumentException("Invalid CV file path.");
        }

        try (InputStream inputStream = cvFilePart.getInputStream()) {
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
        }

        return "uploads/cv/" + storedFileName;
    }

    private String sanitizeFileName(String fileName) {
        if (fileName == null) {
            return "";
        }
        String cleaned = fileName.replace("\\", "/");
        int slashIndex = cleaned.lastIndexOf('/');
        if (slashIndex >= 0) {
            cleaned = cleaned.substring(slashIndex + 1);
        }
        return cleaned.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
