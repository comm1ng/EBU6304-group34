package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.AppConstants;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (getCurrentUser(request) != null) {
            redirect(response, request, "/dashboard");
            return;
        }
        forward(request, response, "login.jsp", null, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identifier = ValidationUtil.safeTrim(request.getParameter("identifier"));
        String password = request.getParameter("password");

        Optional<User> userOptional = container().getAuthService().login(identifier, password);
        if (userOptional.isEmpty()) {
            request.setAttribute("error", "Invalid account information or password.");
            request.setAttribute("identifier", identifier);
            forward(request, response, "login.jsp", null, null);
            return;
        }

        User user = userOptional.get();
        HttpSession session = request.getSession();
        session.setAttribute(AppConstants.SESSION_USER_ID, user.getId());

        if (user.getRoles().size() == 1) {
            setActiveRole(request, user.getRoles().get(0));
            redirect(response, request, "/dashboard");
            return;
        }

        session.removeAttribute(AppConstants.SESSION_ACTIVE_ROLE);
        setFlashSuccess(request, "Authentication successful. Please choose a role for this session.");
        redirect(response, request, "/role-select");
    }
}
