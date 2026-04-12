<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>TA Workload Summary</h2>
    <p class="text-muted">Workload is calculated from accepted jobs and total weekly hours.</p>
</section>

<section class="card">
    <table class="table">
        <thead>
        <tr>
            <th>TA ID</th>
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
                <td colspan="5"><div class="empty-state"><strong>No workload records available.</strong></div></td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>

<%@ include file="common/footer.jspf" %>

