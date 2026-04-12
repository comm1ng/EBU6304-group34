package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "roleSelectServlet", urlPatterns = {"/role-select"})
public class RoleSelectServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireLogin(request, response);
        if (user == null) {
            return;
        }

        if (user.getRoles().size() == 1) {
            setActiveRole(request, user.getRoles().get(0));
            redirect(response, request, "/dashboard");
            return;
        }

        forward(request, response, "roleSelect.jsp", user, getActiveRole(request));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireLogin(request, response);
        if (user == null) {
            return;
        }

        String roleValue = ValidationUtil.safeTrim(request.getParameter("role"));
        Role selectedRole;
        try {
            selectedRole = Role.valueOf(roleValue);
        } catch (Exception ex) {
            request.setAttribute("error", "Please select a valid role.");
            forward(request, response, "roleSelect.jsp", user, getActiveRole(request));
            return;
        }

        if (!user.hasRole(selectedRole)) {
            request.setAttribute("error", "You do not have access to the selected role.");
            forward(request, response, "roleSelect.jsp", user, getActiveRole(request));
            return;
        }

        setActiveRole(request, selectedRole);
        setFlashSuccess(request, "Switched to " + selectedRole + " role.");
        redirect(response, request, "/dashboard");
    }
}
