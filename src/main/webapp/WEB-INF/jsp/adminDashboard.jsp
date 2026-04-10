<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-header">
    <h1>Admin Dashboard</h1>
    <p class="muted">Monitor TA workload and system-wide recruitment activity.</p>
</section>

<section class="card-grid">
    <article class="card stat-card">
        <h3>Total Users</h3>
        <p class="stat-number">${stats.totalUsers}</p>
        <p class="muted">TA ${stats.totalTaUsers} · MO ${stats.totalMoUsers} · Admin ${stats.totalAdminUsers}</p>
    </article>
    <article class="card stat-card">
        <h3>Total Jobs</h3>
        <p class="stat-number">${stats.totalJobs}</p>
    </article>
    <article class="card stat-card">
        <h3>Total Applications</h3>
        <p class="stat-number">${stats.totalApplications}</p>
    </article>
</section>

<section class="card">
    <h2>TA Workload Summary</h2>
    <table class="data-table">
        <thead>
        <tr>
            <th>TA User ID</th>
            <th>Name</th>
            <th>Accepted Jobs</th>
            <th>Total Hours/Week</th>
            <th>Pending Applications</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${summaries}" var="item">
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
                <td colspan="5">No TA workload data available.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>
