package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.model.WorkloadSummary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "workloadSummaryServlet", urlPatterns = {"/admin/workload-summary"})
public class WorkloadSummaryServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.ADMIN);
        if (user == null) {
            return;
        }

        List<WorkloadSummary> summaries = container().getAdminService().getWorkloadSummary();
        request.setAttribute("summaries", summaries);

        forward(request, response, "workloadSummary.jsp", user, Role.ADMIN);
    }
}

