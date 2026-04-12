<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>System Overview</h2>
    <p class="text-muted">Live operational snapshot of recruitment activities.</p>
</section>

<section class="stats-grid">
    <article class="metric-card">
        <p class="metric-label">Total Users</p>
        <p class="metric-value">${stats.totalUsers}</p>
    </article>
    <article class="metric-card">
        <p class="metric-label">TA Count</p>
        <p class="metric-value">${stats.totalTaUsers}</p>
    </article>
    <article class="metric-card">
        <p class="metric-label">MO Count</p>
        <p class="metric-value">${stats.totalMoUsers}</p>
    </article>
    <article class="metric-card">
        <p class="metric-label">Open Jobs</p>
        <p class="metric-value">${stats.openJobs}</p>
    </article>
    <article class="metric-card">
        <p class="metric-label">Total Applications</p>
        <p class="metric-value">${stats.totalApplications}</p>
    </article>
    <article class="metric-card">
        <p class="metric-label">Accepted Applications</p>
        <p class="metric-value">${stats.acceptedApplications}</p>
    </article>
</section>

<section class="card">
    <h3>Latest Job Postings</h3>
    <table class="table">
        <thead>
        <tr>
            <th>Job ID</th>
            <th>Title</th>
            <th>Status</th>
            <th>Created At</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${latestJobs}" var="job">
            <tr>
                <td>${job.id}</td>
                <td>${job.title}</td>
                <td><span class="badge badge-${job.status}">${job.status}</span></td>
                <td>${job.createdAt}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty latestJobs}">
            <tr><td colspan="4"><div class="empty-state"><strong>No job postings available.</strong></div></td></tr>
        </c:if>
        </tbody>
    </table>
</section>

<section class="card">
    <h3>Latest Applications</h3>
    <table class="table">
        <thead>
        <tr>
            <th>Application ID</th>
            <th>Candidate</th>
            <th>Job</th>
            <th>Status</th>
            <th>Applied At</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${latestApplications}" var="app">
            <c:set var="candidate" value="${userById[app.taUserId]}"/>
            <c:set var="job" value="${jobById[app.jobId]}"/>
            <tr>
                <td>${app.id}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty candidate}">${candidate.fullName}</c:when>
                        <c:otherwise>${app.taUserId}</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty job}">${job.title}</c:when>
                        <c:otherwise>${app.jobId}</c:otherwise>
                    </c:choose>
                </td>
                <td><span class="badge badge-${app.status}">${app.status}</span></td>
                <td>${app.appliedAt}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty latestApplications}">
            <tr><td colspan="5"><div class="empty-state"><strong>No applications available.</strong></div></td></tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

