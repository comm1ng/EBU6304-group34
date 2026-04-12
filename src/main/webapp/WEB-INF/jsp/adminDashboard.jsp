<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>Admin Dashboard</h2>
    <p class="text-muted">Monitor platform health, TA workload, and recruitment outcomes.</p>
</section>

<section class="stats-grid">
    <article class="metric-card">
        <p class="metric-label">Total Users</p>
        <p class="metric-value">${stats.totalUsers}</p>
        <span class="text-muted">TA ${stats.totalTaUsers} | MO ${stats.totalMoUsers} | Admin ${stats.totalAdminUsers}</span>
    </article>
    <article class="metric-card">
        <p class="metric-label">Total Jobs</p>
        <p class="metric-value">${stats.totalJobs}</p>
        <span class="text-muted">Open: ${stats.openJobs}</span>
    </article>
    <article class="metric-card">
        <p class="metric-label">Total Applications</p>
        <p class="metric-value">${stats.totalApplications}</p>
        <span class="text-muted">Accepted: ${stats.acceptedApplications}</span>
    </article>
</section>

<section class="card">
    <div class="card-header-row">
        <h3>Top Workload Snapshot</h3>
        <a href="${pageContext.request.contextPath}/admin/workload-summary">Open full workload summary</a>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>TA ID</th>
            <th>Name</th>
            <th>Accepted Jobs</th>
            <th>Total Hours/Week</th>
            <th>Pending Applications</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${summaries}" var="item" end="4">
            <tr>
                <td>${item.taUserId}</td>
                <td>${item.taName}</td>
                <td>${item.acceptedJobs}</td>
                <td>${item.totalHoursPerWeek}</td>
                <td>${item.pendingApplications}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty summaries}">
            <tr>
                <td colspan="5"><div class="empty-state"><strong>No workload data yet.</strong></div></td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

