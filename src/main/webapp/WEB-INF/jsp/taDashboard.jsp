<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-header">
    <h1>TA Dashboard</h1>
    <p class="muted">Browse available jobs, apply, and track your application results.</p>
</section>

<section class="card-grid">
    <article class="card stat-card">
        <h3>Open Jobs</h3>
        <p class="stat-number">${openJobsCount}</p>
        <a href="${pageContext.request.contextPath}/jobs" class="text-link">Browse jobs</a>
    </article>
    <article class="card stat-card">
        <h3>My Applications</h3>
        <p class="stat-number">${myApplicationsCount}</p>
        <a href="${pageContext.request.contextPath}/my-applications" class="text-link">View all</a>
    </article>
    <article class="card stat-card">
        <h3>Pending</h3>
        <p class="stat-number">${pendingCount}</p>
    </article>
    <article class="card stat-card">
        <h3>Accepted / Rejected</h3>
        <p class="stat-number">${acceptedCount} / ${rejectedCount}</p>
    </article>
</section>

<section class="card">
    <h2>Recent Applications</h2>
    <table class="data-table">
        <thead>
        <tr>
            <th>Application ID</th>
            <th>Status</th>
            <th>Applied At</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${latestApplications}" var="app">
            <tr>
                <td>${app.id}</td>
                <td><span class="status ${app.status}">${app.status}</span></td>
                <td>${app.appliedAt}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty latestApplications}">
            <tr>
                <td colspan="3">No applications yet.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>
