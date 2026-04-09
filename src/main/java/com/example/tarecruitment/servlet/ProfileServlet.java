package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.MOProfile;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.CsvUtil;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "profileServlet", urlPatterns = {"/profile"})
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
                profile.setCvFilePath(ValidationUtil.safeTrim(request.getParameter("cvFilePath")));
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
}
