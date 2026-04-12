<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In | Teaching Assistant Recruitment Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login-premium.css">
</head>
<body class="login-page">
<div class="login-wrapper">
    <section class="brand-side">
        <div class="brand-header">
            <img src="${pageContext.request.contextPath}/assets/images/bupt-school-logo.png" alt="BUPT International School Logo" class="brand-logo">
        </div>

        <div class="brand-content">
            <h1>Teaching Assistant<br>Recruitment Platform</h1>
            <p>Sign in to your account to access the Teaching Assistant Recruitment Platform.</p>
        </div>
    </section>

    <section class="form-side">
        <article class="login-card">
            <h2>Sign In</h2>
            <p class="login-desc">Use your username or email, the system detects your role after authentication.</p>

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

            <form method="post" action="${pageContext.request.contextPath}/login" class="login-form" novalidate>
                <label for="identifier" class="sr-only">Account / Email</label>
                <input id="identifier" name="identifier" type="text" value="${identifier}" placeholder="Account / Email" required>

                <label for="password" class="sr-only">Password</label>
                <input id="password" name="password" type="password" placeholder="Password" required>

                <button type="submit" class="btn-login">Login</button>
            </form>

            <div class="login-actions">
                <div>
                    <p class="action-label">No account yet?</p>
                    <a href="${pageContext.request.contextPath}/register" class="register-link">Register as TA or MO</a>
                </div>
                <a href="#" class="forgot-link">Forgot password?</a>
            </div>
        </article>
    </section>
</div>

<footer class="login-footer">© 2024 International School of Beijing University of Posts and Telecommunications. All rights reserved.</footer>
</body>
</html>
