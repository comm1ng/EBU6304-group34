package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = requireLogin(request, response);
        if (user == null) {
            return;
        }

        Role activeRole = getActiveRole(request);
        if (activeRole == null || !user.hasRole(activeRole)) {
            if (user.getRoles().size() == 1) {
                activeRole = user.getRoles().get(0);
                setActiveRole(request, activeRole);
            } else {
                redirect(response, request, "/role-select");
                return;
            }
        }

        if (activeRole == Role.TA) {
            redirect(response, request, "/ta/dashboard");
            return;
        }
        if (activeRole == Role.MO) {
            redirect(response, request, "/mo/dashboard");
            return;
        }
        if (activeRole == Role.ADMIN) {
            redirect(response, request, "/admin/dashboard");
            return;
        }

        setFlashError(request, "Unable to determine your role.");
        redirect(response, request, "/login");
    }
}
