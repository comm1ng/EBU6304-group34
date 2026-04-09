package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.SystemStats;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.model.WorkloadSummary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "adminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.ADMIN);
        if (user == null) {
            return;
        }

        List<WorkloadSummary> summaries = container().getAdminService().getWorkloadSummary();
        SystemStats stats = container().getAdminService().getSystemStats();

        request.setAttribute("summaries", summaries);
        request.setAttribute("stats", stats);

        forward(request, response, "adminDashboard.jsp", user, Role.ADMIN);
    }
}
