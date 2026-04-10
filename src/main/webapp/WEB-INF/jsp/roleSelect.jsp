<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="auth-page single-column">
    <article class="card auth-card">
        <h2>Select Workspace Role</h2>
        <p class="text-muted">This account has both TA and MO roles. Choose the role for this session.</p>

        <form method="post" action="${pageContext.request.contextPath}/role-select" class="form-grid">
            <label for="role">Role</label>
            <select id="role" name="role" required>
                <option value="">Select role</option>
                <c:forEach items="${availableRoles}" var="roleItem">
                    <option value="${roleItem}">${roleItem}</option>
                </c:forEach>
            </select>
            <button class="btn" type="submit">Enter Dashboard</button>
        </form>
    </article>
</section>

<%@ include file="common/footer.jspf" %>

