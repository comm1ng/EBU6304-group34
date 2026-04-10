<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-header">
    <h1>MO Dashboard</h1>
    <p class="muted">Post jobs and manage applicants for your modules or activities.</p>
</section>

<section class="card-grid">
    <article class="card stat-card">
        <h3>My Jobs</h3>
        <p class="stat-number">${myJobsCount}</p>
    </article>
    <article class="card stat-card">
        <h3>Open Jobs</h3>
        <p class="stat-number">${openJobsCount}</p>
    </article>
    <article class="card stat-card">
        <h3>Pending Applicants</h3>
        <p class="stat-number">${pendingApplicants}</p>
    </article>
    <article class="card stat-card">
        <h3>Quick Action</h3>
        <a class="btn mt-8" href="${pageContext.request.contextPath}/mo/post-job">Post New Job</a>
    </article>
</section>

<section class="card">
    <h2>Jobs Posted By You</h2>
    <table class="data-table">
        <thead>
        <tr>
            <th>Job ID</th>
            <th>Title</th>
            <th>Hours/Week</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${myJobs}" var="job">
            <tr>
                <td>${job.id}</td>
                <td>${job.title}</td>
                <td>${job.hoursPerWeek}</td>
                <td><span class="status ${job.status}">${job.status}</span></td>
                <td>
                    <a class="text-link" href="${pageContext.request.contextPath}/mo/manage-applicants?jobId=${job.id}">Manage Applicants</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty myJobs}">
            <tr>
                <td colspan="5">No jobs posted yet.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>
