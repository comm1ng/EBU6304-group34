<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>Applicant Profile</h2>
    <p class="text-muted">Job: <strong>${job.title}</strong> (${job.id})</p>
</section>

<section class="card">
    <h3>Applicant Basic Information</h3>
    <table class="table">
        <tbody>
        <tr>
            <th>User ID</th>
            <td>${applicant.id}</td>
        </tr>
        <tr>
            <th>Full Name</th>
            <td>${applicant.fullName}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${applicant.email}</td>
        </tr>
        <tr>
            <th>Application Status</th>
            <td><span class="badge badge-${application.status}">${application.status}</span></td>
        </tr>
        <tr>
            <th>Applied At</th>
            <td>${application.appliedAt}</td>
        </tr>
        </tbody>
    </table>
</section>

<section class="card">
    <h3>TA Profile</h3>
    <table class="table">
        <tbody>
        <tr>
            <th>Major</th>
            <td>${taProfile.major}</td>
        </tr>
        <tr>
            <th>Academic Year</th>
            <td>${taProfile.academicYear}</td>
        </tr>
        <tr>
            <th>Skills</th>
            <td>${taProfile.skills}</td>
        </tr>
        <tr>
            <th>CV Summary</th>
            <td>${taProfile.cvSummary}</td>
        </tr>
        <tr>
            <th>Experience</th>
            <td>${taProfile.experience}</td>
        </tr>
        <tr>
            <th>CV File</th>
            <td>
                <c:choose>
                    <c:when test="${not empty taProfile.cvFilePath}">
                        <a href="${pageContext.request.contextPath}/cv-file?taUserId=${applicant.id}&jobId=${job.id}" target="_blank">View / Download CV</a>
                        <p class="cell-sub">Stored path: ${taProfile.cvFilePath}</p>
                    </c:when>
                    <c:otherwise>
                        CV not uploaded.
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="button-row top-gap">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/mo/manage-applicants?jobId=${job.id}">Back to Applicants</a>
    </div>
</section>

<%@ include file="common/footer.jspf" %>
