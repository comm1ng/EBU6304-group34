<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="auth-wrapper">
    <div class="card auth-card wide">
        <h1>Register</h1>
        <p class="muted">Self-registration is open for TA and MO only. Admin accounts are predefined.</p>

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

            <label for="password">Password (min 6 chars)</label>
            <input id="password" name="password" type="password" required>

            <label for="role">Role</label>
            <select id="role" name="role" required>
                <option value="">Select role</option>
                <option value="TA" <c:if test="${role == 'TA'}">selected</c:if>>TA</option>
                <option value="MO" <c:if test="${role == 'MO'}">selected</c:if>>MO</option>
            </select>

            <div id="workUnitRow">
                <label for="workUnit">Work Unit / Organisation (MO required)</label>
                <input id="workUnit" name="workUnit" type="text" value="${workUnit}">
            </div>

            <c:choose>
                <c:when test="${confirmRequired}">
                    <div class="button-row">
                        <button class="btn" type="submit" name="confirmDecision" value="continue">Continue Registration</button>
                        <button class="btn btn-outline" type="submit" name="confirmDecision" value="cancel">Cancel</button>
                    </div>
                </c:when>
                <c:otherwise>
                    <button class="btn" type="submit">Register</button>
                </c:otherwise>
            </c:choose>
        </form>

        <p class="mt-16">Already have an account? <a href="${pageContext.request.contextPath}/login">Back to login</a></p>
    </div>
</section>

<script>
    (function () {
        const roleSelect = document.getElementById('role');
        const workUnitRow = document.getElementById('workUnitRow');
        const workUnitInput = document.getElementById('workUnit');
        function toggleWorkUnit() {
            const isMo = roleSelect.value === 'MO';
            workUnitRow.style.display = isMo ? 'block' : 'none';
            workUnitInput.required = isMo;
        }
        roleSelect.addEventListener('change', toggleWorkUnit);
        toggleWorkUnit();
    })();
</script>

<%@ include file="common/footer.jspf" %>
