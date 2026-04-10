<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="card max-640">
    <h1>Select Role</h1>
    <p class="muted">Authentication succeeded. This account has multiple roles. Choose one role for this session.</p>

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
</section>

<%@ include file="common/footer.jspf" %>
