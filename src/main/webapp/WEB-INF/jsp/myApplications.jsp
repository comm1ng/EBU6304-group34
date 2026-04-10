<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>My Applications</h2>
    <p class="text-muted">Review all submitted applications and latest decisions.</p>
</section>

<section class="card">
    <table class="table">
        <thead>
        <tr>
            <th>Application ID</th>
            <th>Job</th>
            <th>Status</th>
            <th>Applied At</th>
            <th>Reviewed At</th>
            <th>Timeline</th>
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
                            <strong>${job.title}</strong>
                            <p class="cell-sub">${job.hoursPerWeek} hrs/week | ${job.locationMode}</p>
                        </c:when>
                        <c:otherwise>
                            Unknown Job (${app.jobId})
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><span class="badge badge-${app.status}">${app.status}</span></td>
                <td>${app.appliedAt}</td>
                <td>
                    <c:choose>
                        <c:when test="${empty app.reviewedAt}">-</c:when>
                        <c:otherwise>${app.reviewedAt}</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <div class="timeline-mini">
                        <span class="dot done"></span><span>Submitted</span>
                        <span class="arrow">-&gt;</span>
                        <c:choose>
                            <c:when test="${app.status == 'PENDING'}">
                                <span class="dot wait"></span>
                            </c:when>
                            <c:otherwise>
                                <span class="dot done"></span>
                            </c:otherwise>
                        </c:choose>
                        <span>
                            <c:choose>
                                <c:when test="${app.status == 'PENDING'}">Under Review</c:when>
                                <c:otherwise>Decision Made</c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty myApplications}">
            <tr>
                <td colspan="6">
                    <div class="empty-state">
                        <strong>No applications submitted yet</strong>
                        <p>Go to Available Jobs and apply to positions that match your skills.</p>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

