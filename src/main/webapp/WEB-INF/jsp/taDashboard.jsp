<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>TA Dashboard</h2>
    <p class="text-muted">Track opportunities, applications, and profile readiness.</p>
</section>

<section class="stats-grid">
    <article class="metric-card">
        <p class="metric-label">Open Jobs</p>
        <p class="metric-value">${openJobsCount}</p>
        <a href="${pageContext.request.contextPath}/jobs">Browse opportunities</a>
    </article>
    <article class="metric-card">
        <p class="metric-label">My Applications</p>
        <p class="metric-value">${myApplicationsCount}</p>
        <a href="${pageContext.request.contextPath}/my-applications">View all applications</a>
    </article>
    <article class="metric-card">
        <p class="metric-label">Pending</p>
        <p class="metric-value">${pendingCount}</p>
        <span class="text-muted">Awaiting MO review</span>
    </article>
    <article class="metric-card">
        <p class="metric-label">Accepted / Rejected</p>
        <p class="metric-value">${acceptedCount} / ${rejectedCount}</p>
        <span class="text-muted">Decision outcomes</span>
    </article>
    <article class="metric-card">
        <p class="metric-label">Profile Completeness</p>
        <p class="metric-value">${profileCompleteness}%</p>
        <a href="${pageContext.request.contextPath}/profile">Update profile</a>
    </article>
</section>

<section class="card">
    <div class="card-header-row">
        <h3>Recent Applications</h3>
        <a href="${pageContext.request.contextPath}/my-applications">Open full list</a>
    </div>
    <table class="table">
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
                <td><span class="badge badge-${app.status}">${app.status}</span></td>
                <td>${app.appliedAt}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty latestApplications}">
            <tr>
                <td colspan="3">
                    <div class="empty-state">
                        <strong>No applications yet</strong>
                        <p>Start by exploring available jobs and submit your first application.</p>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

