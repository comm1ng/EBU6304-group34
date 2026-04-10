<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-header">
    <h1>My Applications</h1>
    <p class="muted">Track application status for all jobs you applied to.</p>
</section>

<section class="card">
    <table class="data-table">
        <thead>
        <tr>
            <th>Application ID</th>
            <th>Job</th>
            <th>Hours/Week</th>
            <th>Status</th>
            <th>Applied At</th>
            <th>Reviewed At</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${myApplications}" var="app">
            <c:set var="job" value="${jobById[app.jobId]}"/>
            <tr>
                <td>${app.id}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty job}">
                            ${job.title} (${job.id})
                        </c:when>
                        <c:otherwise>
                            Unknown Job (${app.jobId})
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty job}">
                            ${job.hoursPerWeek}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td><span class="status ${app.status}">${app.status}</span></td>
                <td>${app.appliedAt}</td>
                <td>
                    <c:choose>
                        <c:when test="${empty app.reviewedAt}">-</c:when>
                        <c:otherwise>${app.reviewedAt}</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty myApplications}">
            <tr>
                <td colspan="6">No applications submitted yet.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>
