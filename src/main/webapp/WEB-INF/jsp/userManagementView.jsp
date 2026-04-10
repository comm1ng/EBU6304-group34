<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>User Directory (Read-only)</h2>
    <p class="text-muted">Operational view of all registered accounts and assigned roles.</p>
</section>

<section class="card">
    <table class="table">
        <thead>
        <tr>
            <th>User ID</th>
            <th>Name</th>
            <th>Username</th>
            <th>Email</th>
            <th>Roles</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="userItem">
            <tr>
                <td>${userItem.id}</td>
                <td>${userItem.fullName}</td>
                <td>${userItem.username}</td>
                <td>${userItem.email}</td>
                <td>
                    <c:forEach items="${userItem.roles}" var="roleItem">
                        <span class="badge badge-role">${roleItem}</span>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty users}">
            <tr>
                <td colspan="5"><div class="empty-state"><strong>No user data available.</strong></div></td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

