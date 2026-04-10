<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>MO Dashboard</h2>
    <p class="text-muted">Post positions, manage job lifecycle, and review applicants.</p>
</section>

<section class="stats-grid">
    <article class="metric-card">
        <p class="metric-label">My Jobs</p>
        <p class="metric-value">${myJobsCount}</p>
    </article>
    <article class="metric-card">
        <p class="metric-label">Open Jobs</p>
        <p class="metric-value">${openJobsCount}</p>
    </article>
    <article class="metric-card">
        <p class="metric-label">Pending Applicants</p>
        <p class="metric-value">${pendingApplicants}</p>
    </article>
    <article class="metric-card">
        <p class="metric-label">Actions</p>
        <div class="stack-actions">
            <a href="${pageContext.request.contextPath}/mo/post-job">Post New Job</a>
            <a href="${pageContext.request.contextPath}/mo/manage-jobs">Manage Existing Jobs</a>
        </div>
    </article>
</section>

<section class="card">
    <div class="card-header-row">
        <h3>Recently Posted Jobs</h3>
        <a href="${pageContext.request.contextPath}/mo/manage-jobs">Go to job management</a>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>Job ID</th>
            <th>Title</th>
            <th>Hours/Week</th>
            <th>Deadline</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${myJobs}" var="job">
            <tr>
                <td>${job.id}</td>
                <td>${job.title}</td>
                <td>${job.hoursPerWeek}</td>
                <td>${job.deadline}</td>
                <td><span class="badge badge-${job.status}">${job.status}</span></td>
                <td><a href="${pageContext.request.contextPath}/mo/manage-applicants?jobId=${job.id}">View Applicants</a></td>
            </tr>
        </c:forEach>
        <c:if test="${empty myJobs}">
            <tr>
                <td colspan="6">
                    <div class="empty-state">
                        <strong>No jobs posted yet</strong>
                        <p>Create your first TA position to start receiving applications.</p>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

