<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account | Teaching Assistant Recruitment Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login-premium.css">
    <script defer src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</head>
<body class="login-page">
<div class="login-wrapper register-wrapper">
    <section class="brand-side">
        <div class="brand-header">
            <img src="${pageContext.request.contextPath}/assets/images/bupt-school-logo.png" alt="BUPT International School Logo" class="brand-logo">
        </div>

        <div class="brand-content">
            <h1>Create your<br>TA or MO account</h1>
            <p>Join the Teaching Assistant Recruitment Platform to manage applications, staffing, and role-based workflows in one place.</p>
        </div>
    </section>

    <section class="form-side">
        <article class="login-card register-card-premium">
            <h2>Create Account</h2>
            <p class="login-desc">Registration is available for Teaching Assistants and Module Organisers only.</p>

            <c:if test="${not empty flashSuccess}">
                <div class="form-alert alert-success">${flashSuccess}</div>
            </c:if>
            <c:if test="${not empty flashError}">
                <div class="form-alert alert-error">${flashError}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="form-alert alert-error">${error}</div>
            </c:if>
            <c:if test="${not empty info}">
                <div class="form-alert alert-info">${info}</div>
            </c:if>
            <c:if test="${confirmRequired}">
                <div class="form-alert register-confirm-alert">${confirmMessage}</div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/register" class="login-form register-form-premium" id="registerForm" novalidate>
                <div class="register-grid-premium">
                    <div class="register-field">
                        <label for="fullName" class="sr-only">Full Name</label>
                        <input id="fullName" name="fullName" type="text" value="${fullName}" placeholder="Full Name" required>
                    </div>

                    <div class="register-field">
                        <label for="email" class="sr-only">Email</label>
                        <input id="email" name="email" type="email" value="${email}" placeholder="Email" required>
                    </div>

                    <div class="register-field">
                        <label for="username" class="sr-only">Username</label>
                        <input id="username" name="username" type="text" value="${username}" placeholder="Username" required>
                    </div>

                    <div class="register-field">
                        <label for="password" class="sr-only">Password</label>
                        <input id="password" name="password" type="password" minlength="6" placeholder="Password" required>
                    </div>

                    <div class="register-field">
                        <label for="role" class="sr-only">Role</label>
                        <select id="role" name="role" data-toggle-workunit="true" required>
                            <option value="">Select role</option>
                            <option value="TA" <c:if test="${role == 'TA'}">selected</c:if>>Teaching Assistant (TA)</option>
                            <option value="MO" <c:if test="${role == 'MO'}">selected</c:if>>Module Organiser (MO)</option>
                        </select>
                    </div>

                    <div id="workUnitBlock" class="register-field register-field-wide">
                        <label for="workUnit" class="sr-only">Work Unit / Organisation</label>
                        <input id="workUnit" name="workUnit" type="text" value="${workUnit}" placeholder="Work Unit / Organisation (required for MO)">
                    </div>
                </div>

                <c:choose>
                    <c:when test="${confirmRequired}">
                        <div class="register-decision-row">
                            <button type="submit" class="btn-login register-action-secondary" name="confirmDecision" value="continue">Yes, Continue</button>
                            <button type="submit" class="btn-login register-action-secondary register-cancel" name="confirmDecision" value="cancel">Cancel</button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn-login">Register</button>
                    </c:otherwise>
                </c:choose>
            </form>

            <div class="login-actions register-actions-premium">
                <div>
                    <p class="action-label">Already registered?</p>
                    <a href="${pageContext.request.contextPath}/login" class="register-link">Back to login</a>
                </div>
                <span class="forgot-link register-side-note">Admin accounts are system-defined.</span>
            </div>
        </article>
    </section>
</div>

<footer class="login-footer">© 2024 International School of Beijing University of Posts and Telecommunications. All rights reserved.</footer>
</body>
</html>
