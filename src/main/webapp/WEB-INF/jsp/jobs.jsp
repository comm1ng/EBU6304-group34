<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-header">
    <h1>Available Jobs</h1>
    <p class="muted">Only OPEN jobs can be applied to. Duplicate applications are prevented.</p>
</section>

<section class="card">
    <table class="data-table">
        <thead>
        <tr>
            <th>Job ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Required Skills</th>
            <th>Hours/Week</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${openJobs}" var="job">
            <c:set var="existingStatus" value="${appliedStatusByJobId[job.id]}"/>
            <tr>
                <td>${job.id}</td>
                <td>${job.title}</td>
                <td>${job.description}</td>
                <td>${job.requiredSkills}</td>
                <td>${job.hoursPerWeek}</td>
                <td><span class="status ${job.status}">${job.status}</span></td>
                <td>
                    <c:choose>
                        <c:when test="${empty existingStatus}">
                            <form method="post" action="${pageContext.request.contextPath}/jobs/apply" class="inline-form">
                                <input type="hidden" name="jobId" value="${job.id}">
                                <button class="btn btn-small" type="submit">Apply</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <span class="status ${existingStatus}">Already Applied (${existingStatus})</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty openJobs}">
            <tr>
                <td colspan="7">No open jobs currently.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>
