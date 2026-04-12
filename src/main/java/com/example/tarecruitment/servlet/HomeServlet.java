package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "homeServlet", urlPatterns = {"/"})
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();

        // Keep static resources accessible when "/" is mapped to this servlet.
        if (requestUri != null && requestUri.startsWith(contextPath + "/assets/")) {
            RequestDispatcher defaultDispatcher = request.getServletContext().getNamedDispatcher("default");
            if (defaultDispatcher != null) {
                defaultDispatcher.forward(request, response);
                return;
            }
        }

        User user = getCurrentUser(request);
        if (user == null) {
            redirect(response, request, "/login");
            return;
        }
        redirect(response, request, "/dashboard");
    }
}
