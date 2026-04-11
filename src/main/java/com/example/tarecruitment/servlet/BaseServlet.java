package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.AppConstants;
import com.example.tarecruitment.util.AppContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class BaseServlet extends HttpServlet {
    protected AppContainer container() {
        return (AppContainer) getServletContext().getAttribute(AppConstants.ATTR_CONTAINER);
    }

    protected User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        String userId = (String) session.getAttribute(AppConstants.SESSION_USER_ID);
        if (userId == null || userId.isBlank()) {
            return null;
        }
        return container().getUserService().findById(userId).orElse(null);
    }

    protected Role getActiveRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        String role = (String) session.getAttribute(AppConstants.SESSION_ACTIVE_ROLE);
        if (role == null || role.isBlank()) {
            return null;
        }
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    protected void setActiveRole(HttpServletRequest request, Role role) {
        if (role == null) {
            request.getSession().removeAttribute(AppConstants.SESSION_ACTIVE_ROLE);
            return;
        }
        request.getSession().setAttribute(AppConstants.SESSION_ACTIVE_ROLE, role.name());
    }

    protected void setFlashSuccess(HttpServletRequest request, String message) {
        request.getSession().setAttribute(AppConstants.SESSION_FLASH_SUCCESS, message);
    }

    protected void setFlashError(HttpServletRequest request, String message) {
        request.getSession().setAttribute(AppConstants.SESSION_FLASH_ERROR, message);
    }

    protected User requireLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = getCurrentUser(request);
        if (user == null) {
            redirect(response, request, "/login");
            return null;
        }
        return user;
    }

    protected User requireRole(HttpServletRequest request, HttpServletResponse response, Role... allowedRoles) throws IOException {
        User user = requireLogin(request, response);
        if (user == null) {
            return null;
        }

        Role activeRole = getActiveRole(request);
        if (activeRole == null || !user.hasRole(activeRole)) {
            if (user.getRoles().size() == 1) {
                activeRole = user.getRoles().get(0);
                setActiveRole(request, activeRole);
            } else {
                redirect(response, request, "/role-select");
                return null;
            }
        }

        for (Role allowedRole : allowedRoles) {
            if (allowedRole == activeRole) {
                return user;
            }
        }

        setFlashError(request, "Permission denied for this page.");
        redirect(response, request, "/dashboard");
        return null;
    }

    protected void redirect(HttpServletResponse response, HttpServletRequest request, String path) throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }

    protected void forward(HttpServletRequest request,
                           HttpServletResponse response,
                           String jspName,
                           User currentUser,
                           Role activeRole) throws ServletException, IOException {
        bindCommonAttributes(request, currentUser, activeRole);
        request.getRequestDispatcher("/WEB-INF/jsp/" + jspName).forward(request, response);
    }

    protected void bindCommonAttributes(HttpServletRequest request, User currentUser, Role activeRole) {
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("activeRole", activeRole == null ? "" : activeRole.name());
        request.setAttribute("availableRoles", currentUser == null ? List.of() : currentUser.getRoles());
        request.setAttribute("hasMultipleRoles", currentUser != null && currentUser.getRoles().size() > 1);

        HttpSession session = request.getSession(false);
        if (session != null) {
            request.setAttribute("flashSuccess", consumeFromSession(session, AppConstants.SESSION_FLASH_SUCCESS));
            request.setAttribute("flashError", consumeFromSession(session, AppConstants.SESSION_FLASH_ERROR));
        }
    }

    protected Path resolveDataDir() {
        String configuredDataDir = System.getProperty("tarecruitment.dataDir");
        if (configuredDataDir != null && !configuredDataDir.isBlank()) {
            return Paths.get(configuredDataDir.trim());
        }

        String contextParam = getServletContext().getInitParameter("dataDir");
        if (contextParam != null && !contextParam.isBlank()) {
            return Paths.get(contextParam.trim());
        }

        return Paths.get("data");
    }

    private String consumeFromSession(HttpSession session, String key) {
        Object value = session.getAttribute(key);
        if (value == null) {
            return null;
        }
        session.removeAttribute(key);
        return value.toString();
    }
}
