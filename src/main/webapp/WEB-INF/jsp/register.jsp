<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="auth-page single-column">
    <article class="card auth-card wide">
        <h2>Create Account</h2>
        <p class="text-muted">Self-registration is available for TA and MO only. Admin accounts are predefined by the system.</p>

        <c:if test="${confirmRequired}">
            <div class="alert alert-warning">
                ${confirmMessage}
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register" class="form-grid" id="registerForm">
            <label for="fullName">Full Name</label>
            <input id="fullName" name="fullName" type="text" value="${fullName}" required>

            <label for="email">Email</label>
            <input id="email" name="email" type="email" value="${email}" required>

            <label for="username">Username</label>
            <input id="username" name="username" type="text" value="${username}" required>

            <label for="password">Password</label>
            <input id="password" name="password" type="password" minlength="6" required>

            <label for="role">Role</label>
            <select id="role" name="role" data-toggle-workunit="true" required>
                <option value="">Select role</option>
                <option value="TA" <c:if test="${role == 'TA'}">selected</c:if>>Teaching Assistant (TA)</option>
                <option value="MO" <c:if test="${role == 'MO'}">selected</c:if>>Module Organiser (MO)</option>
            </select>

            <div id="workUnitBlock" class="field-group">
                <label for="workUnit">Work Unit / Organisation (MO Required)</label>
                <input id="workUnit" name="workUnit" type="text" value="${workUnit}" placeholder="e.g. BUPT International School">
            </div>

            <c:choose>
                <c:when test="${confirmRequired}">
                    <div class="button-row">
                        <button class="btn" type="submit" name="confirmDecision" value="continue">Yes, Continue</button>
                        <button class="btn btn-secondary" type="submit" name="confirmDecision" value="cancel">Cancel</button>
                    </div>
                </c:when>
                <c:otherwise>
                    <button class="btn" type="submit">Register</button>
                </c:otherwise>
            </c:choose>
        </form>

        <p class="helper-link">Already registered? <a href="${pageContext.request.contextPath}/login">Back to login</a></p>
    </article>
</section>

<%@ include file="common/footer.jspf" %>

