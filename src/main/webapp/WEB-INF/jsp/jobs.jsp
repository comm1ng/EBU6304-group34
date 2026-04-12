<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>Available Jobs</h2>
    <p class="text-muted">Search and apply for open TA opportunities.</p>
</section>

<section class="card">
    <form method="get" action="${pageContext.request.contextPath}/jobs" class="filter-grid">
        <div>
            <label for="keyword">Keyword</label>
            <input id="keyword" name="keyword" type="text" value="${keyword}" placeholder="title, skill, description">
        </div>
        <div>
            <label for="mode">Location / Mode</label>
            <select id="mode" name="mode">
                <option value="">All</option>
                <option value="On-campus" <c:if test="${mode == 'On-campus'}">selected</c:if>>On-campus</option>
                <option value="Online" <c:if test="${mode == 'Online'}">selected</c:if>>Online</option>
                <option value="Hybrid" <c:if test="${mode == 'Hybrid'}">selected</c:if>>Hybrid</option>
            </select>
        </div>
        <div class="filter-actions">
            <button class="btn" type="submit">Search</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/jobs">Reset</a>
        </div>
    </form>
</section>

<section class="card">
    <table class="table">
        <thead>
        <tr>
            <th>Title</th>
            <th>Required Skills</th>
            <th>Hours/Week</th>
            <th>Deadline</th>
            <th>Mode</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${openJobs}" var="job">
            <c:set var="existingStatus" value="${appliedStatusByJobId[job.id]}"/>
            <tr>
                <td>
                    <strong>${job.title}</strong>
                    <p class="cell-sub">${job.description}</p>
                </td>
                <td>${job.requiredSkills}</td>
                <td>${job.hoursPerWeek}</td>
                <td>${job.deadline}</td>
                <td>${job.locationMode}</td>
                <td><span class="badge badge-${job.status}">${job.status}</span></td>
                <td>
                    <c:choose>
                        <c:when test="${empty existingStatus}">
                            <form method="post" action="${pageContext.request.contextPath}/jobs/apply" class="inline-form">
                                <input type="hidden" name="jobId" value="${job.id}">
                                <button class="btn btn-small" type="submit">Apply</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-${existingStatus}">Applied (${existingStatus})</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty openJobs}">
            <tr>
                <td colspan="7">
                    <div class="empty-state">
                        <strong>No jobs match your filter</strong>
                        <p>Try a different keyword or clear filters.</p>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

