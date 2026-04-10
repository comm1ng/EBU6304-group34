package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "userManagementViewServlet", urlPatterns = {"/admin/user-management"})
public class UserManagementViewServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireRole(request, response, Role.ADMIN);
        if (user == null) {
            return;
        }

        List<User> users = container().getUserService().getAllUsers();
        request.setAttribute("users", users);

        forward(request, response, "userManagementView.jsp", user, Role.ADMIN);
    }
}

