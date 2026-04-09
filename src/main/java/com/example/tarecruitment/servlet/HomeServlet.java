package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "homeServlet", urlPatterns = {"/"})
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = getCurrentUser(request);
        if (user == null) {
            redirect(response, request, "/login");
            return;
        }
        redirect(response, request, "/dashboard");
    }
}
