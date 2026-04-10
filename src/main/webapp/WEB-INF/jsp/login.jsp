<%@ include file="common/header.jspf" %>

<section class="auth-wrapper">
    <div class="card auth-card">
        <h1>Login</h1>
        <p class="muted">Enter account (username or email) and password. Role will be detected automatically.</p>

        <form method="post" action="${pageContext.request.contextPath}/login" class="form-grid">
            <label for="identifier">Account / Email</label>
            <input id="identifier" name="identifier" type="text" value="${identifier}" required>

            <label for="password">Password</label>
            <input id="password" name="password" type="password" required>

            <button class="btn" type="submit">Login</button>
        </form>

        <div class="panel mt-16">
            <h3>Demo Accounts</h3>
            <p><strong>TA:</strong> ta1 / 123456</p>
            <p><strong>MO:</strong> mo1 / 123456</p>
            <p><strong>Admin:</strong> admin / admin123</p>
            <p><strong>Dual-role:</strong> dual1 / 123456</p>
        </div>

        <p class="mt-16">No account yet? <a href="${pageContext.request.contextPath}/register">Register as TA or MO</a></p>
    </div>
</section>

<%@ include file="common/footer.jspf" %>
