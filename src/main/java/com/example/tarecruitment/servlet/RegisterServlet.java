package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.service.CrossRoleRegistrationRequiredException;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "registerServlet", urlPatterns = {"/register"})
public class RegisterServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, "register.jsp", null, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = ValidationUtil.safeTrim(request.getParameter("username"));
        String password = request.getParameter("password");
        String fullName = ValidationUtil.safeTrim(request.getParameter("fullName"));
        String email = ValidationUtil.safeTrim(request.getParameter("email"));
        String roleText = ValidationUtil.safeTrim(request.getParameter("role"));
        String workUnit = ValidationUtil.safeTrim(request.getParameter("workUnit"));
        String confirmDecision = ValidationUtil.safeTrim(request.getParameter("confirmDecision"));

        if ("cancel".equalsIgnoreCase(confirmDecision)) {
            request.setAttribute("info", "Registration cancelled.");
            bindForm(request, username, fullName, email, roleText, workUnit);
            forward(request, response, "register.jsp", null, null);
            return;
        }

        Role requestedRole;
        try {
            requestedRole = Role.valueOf(roleText);
        } catch (Exception ex) {
            request.setAttribute("error", "Please select a valid role (TA or MO).");
            bindForm(request, username, fullName, email, roleText, workUnit);
            forward(request, response, "register.jsp", null, null);
            return;
        }

        boolean confirmCrossRole = "continue".equalsIgnoreCase(confirmDecision)
                || "true".equalsIgnoreCase(request.getParameter("confirmCrossRole"));

        try {
            User user = container().getRegistrationService().register(
                    username,
                    password,
                    fullName,
                    email,
                    requestedRole,
                    workUnit,
                    confirmCrossRole
            );
            setFlashSuccess(request, "Registration successful for " + requestedRole + ". Please login with your account.");
            if (user != null) {
                redirect(response, request, "/login");
                return;
            }
        } catch (CrossRoleRegistrationRequiredException ex) {
            request.setAttribute("confirmRequired", true);
            request.setAttribute("confirmMessage", ex.getMessage());
            request.setAttribute("confirmRole", requestedRole.name());
            bindForm(request, username, fullName, email, roleText, workUnit);
            forward(request, response, "register.jsp", null, null);
            return;
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            bindForm(request, username, fullName, email, roleText, workUnit);
            forward(request, response, "register.jsp", null, null);
            return;
        }

        request.setAttribute("error", "Unexpected registration error.");
        bindForm(request, username, fullName, email, roleText, workUnit);
        forward(request, response, "register.jsp", null, null);
    }

    private void bindForm(HttpServletRequest request, String username, String fullName, String email, String role, String workUnit) {
        request.setAttribute("username", username);
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("role", role);
        request.setAttribute("workUnit", workUnit);
    }
}
