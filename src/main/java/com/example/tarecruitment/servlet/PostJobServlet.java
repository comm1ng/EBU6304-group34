package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "postJobServlet", urlPatterns = {"/mo/post-job"})
public class PostJobServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.MO);
        if (user == null) {
            return;
        }
        forward(request, response, "postJob.jsp", user, Role.MO);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.MO);
        if (user == null) {
            return;
        }

        String title = ValidationUtil.safeTrim(request.getParameter("title"));
        String description = ValidationUtil.safeTrim(request.getParameter("description"));
        String requiredSkills = ValidationUtil.safeTrim(request.getParameter("requiredSkills"));
        String hoursPerWeekText = ValidationUtil.safeTrim(request.getParameter("hoursPerWeek"));

        try {
            int hoursPerWeek = ValidationUtil.parsePositiveInteger(hoursPerWeekText, "Hours per week");
            container().getJobService().postJob(title, description, requiredSkills, hoursPerWeek, user.getId());
            setFlashSuccess(request, "Job posted successfully.");
            redirect(response, request, "/mo/dashboard");
            return;
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("requiredSkills", requiredSkills);
            request.setAttribute("hoursPerWeek", hoursPerWeekText);
            forward(request, response, "postJob.jsp", user, Role.MO);
        }
    }
}
