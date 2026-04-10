<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>Manage Jobs</h2>
    <p class="text-muted">Review all postings, update status, and open applicant lists.</p>
</section>

<section class="card">
    <div class="card-header-row">
        <h3>My Posted Jobs</h3>
        <a href="${pageContext.request.contextPath}/mo/post-job">Post New Job</a>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>Job ID</th>
            <th>Title</th>
            <th>Hours</th>
            <th>Deadline</th>
            <th>Mode</th>
            <th>Applicants</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${myJobs}" var="job">
            <tr>
                <td>${job.id}</td>
                <td>
                    <strong>${job.title}</strong>
                    <p class="cell-sub">${job.description}</p>
                </td>
                <td>${job.hoursPerWeek}</td>
                <td>${job.deadline}</td>
                <td>${job.locationMode}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty applicantCountByJobId[job.id]}">${applicantCountByJobId[job.id]}</c:when>
                        <c:otherwise>0</c:otherwise>
                    </c:choose>
                </td>
                <td><span class="badge badge-${job.status}">${job.status}</span></td>
                <td>
                    <div class="action-wrap">
                        <a href="${pageContext.request.contextPath}/mo/manage-applicants?jobId=${job.id}">Applicants</a>
                        <c:if test="${job.status == 'OPEN'}">
                            <form method="post" action="${pageContext.request.contextPath}/mo/update-job-status" class="inline-form">
                                <input type="hidden" name="jobId" value="${job.id}">
                                <input type="hidden" name="status" value="CLOSED">
                                <button class="btn btn-small btn-secondary" type="submit">Close</button>
                            </form>
                        </c:if>
                        <c:if test="${job.status == 'CLOSED'}">
                            <form method="post" action="${pageContext.request.contextPath}/mo/update-job-status" class="inline-form">
                                <input type="hidden" name="jobId" value="${job.id}">
                                <input type="hidden" name="status" value="OPEN">
                                <button class="btn btn-small" type="submit">Reopen</button>
                            </form>
                        </c:if>
                    </div>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty myJobs}">
            <tr>
                <td colspan="8">
                    <div class="empty-state">
                        <strong>No jobs available</strong>
                        <p>Create your first job posting to start recruitment.</p>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

