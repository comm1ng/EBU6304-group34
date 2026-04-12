<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>Applicant Management</h2>
    <p class="text-muted">Job: <strong>${job.title}</strong> (${job.id}) | ${job.hoursPerWeek} hours/week</p>
</section>

<section class="card">
    <table class="table">
        <thead>
        <tr>
            <th>Application ID</th>
            <th>Applicant</th>
            <th>Email</th>
            <th>Status</th>
            <th>Applied At</th>
            <th>Update Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${applications}" var="app">
            <c:set var="ta" value="${taById[app.taUserId]}"/>
            <tr>
                <td>${app.id}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty ta}">${ta.fullName}</c:when>
                        <c:otherwise>${app.taUserId}</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty ta}">${ta.email}</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td><span class="badge badge-${app.status}">${app.status}</span></td>
                <td>${app.appliedAt}</td>
                <td>
                    <div class="action-wrap">
                        <form method="post" action="${pageContext.request.contextPath}/mo/update-application-status" class="inline-form">
                            <input type="hidden" name="applicationId" value="${app.id}">
                            <input type="hidden" name="jobId" value="${job.id}">
                            <input type="hidden" name="status" value="ACCEPTED">
                            <button class="btn btn-small" type="submit">Accept</button>
                        </form>
                        <form method="post" action="${pageContext.request.contextPath}/mo/update-application-status" class="inline-form">
                            <input type="hidden" name="applicationId" value="${app.id}">
                            <input type="hidden" name="jobId" value="${job.id}">
                            <input type="hidden" name="status" value="REJECTED">
                            <button class="btn btn-small btn-danger" type="submit">Reject</button>
                        </form>
                        <form method="post" action="${pageContext.request.contextPath}/mo/update-application-status" class="inline-form">
                            <input type="hidden" name="applicationId" value="${app.id}">
                            <input type="hidden" name="jobId" value="${job.id}">
                            <input type="hidden" name="status" value="PENDING">
                            <button class="btn btn-small btn-secondary" type="submit">Keep Pending</button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty applications}">
            <tr>
                <td colspan="6">
                    <div class="empty-state">
                        <strong>No applicants yet</strong>
                        <p>Share this job posting with suitable TA candidates.</p>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <div class="button-row top-gap">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/mo/manage-jobs">Back to Manage Jobs</a>
    </div>
</section>

<%@ include file="common/footer.jspf" %>

