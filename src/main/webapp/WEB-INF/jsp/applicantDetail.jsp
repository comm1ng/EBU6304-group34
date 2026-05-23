<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="profile-hero">
    <div class="profile-identity">
        <div class="profile-avatar" aria-hidden="true">TA</div>
        <div>
            <p class="profile-kicker">Applicant Profile</p>
            <h2>${applicant.fullName}</h2>
            <p class="text-muted">${applicant.email}</p>
        </div>
    </div>
    <div class="profile-hero-meta">
        <span class="badge badge-${application.status}">${application.status}</span>
        <span>${job.title} (${job.id})</span>
        <span>Applied ${application.appliedAt}</span>
    </div>
</section>

<section class="detail-layout">
    <article class="card detail-card">
        <div class="card-header-row">
            <div>
                <h3>Application</h3>
                <p class="text-muted">Basic applicant and application details.</p>
            </div>
        </div>

        <div class="detail-grid">
            <div class="detail-item">
                <span>User ID</span>
                <strong>${applicant.id}</strong>
            </div>
            <div class="detail-item">
                <span>Full Name</span>
                <strong>${applicant.fullName}</strong>
            </div>
            <div class="detail-item">
                <span>Email</span>
                <strong>${applicant.email}</strong>
            </div>
            <div class="detail-item">
                <span>Status</span>
                <strong><span class="badge badge-${application.status}">${application.status}</span></strong>
            </div>
            <div class="detail-item detail-span">
                <span>Applied At</span>
                <strong>${application.appliedAt}</strong>
            </div>
        </div>
    </article>

    <article class="card detail-card">
        <div class="card-header-row">
            <div>
                <h3>Academic Fit</h3>
                <p class="text-muted">Profile information used during applicant review.</p>
            </div>
        </div>

        <div class="detail-grid">
            <div class="detail-item">
                <span>Major</span>
                <strong>${taProfile.major}</strong>
            </div>
            <div class="detail-item">
                <span>Academic Year</span>
                <strong>${taProfile.academicYear}</strong>
            </div>
            <div class="detail-item detail-span">
                <span>Skills</span>
                <div class="skill-pills">
                    <c:forEach items="${taProfile.skills}" var="skill">
                        <span>${skill}</span>
                    </c:forEach>
                </div>
            </div>
        </div>
    </article>
</section>

<section class="card detail-card">
    <div class="card-header-row">
        <div>
            <h3>CV & Experience</h3>
            <p class="text-muted">Review the applicant's CV summary, experience, and uploaded resume.</p>
        </div>
    </div>

    <div class="profile-narrative">
        <div>
            <span>CV Summary</span>
            <p>${taProfile.cvSummary}</p>
        </div>
        <div>
            <span>Experience</span>
            <p>${taProfile.experience}</p>
        </div>
    </div>

    <div class="cv-panel">
        <div>
            <strong>Uploaded CV</strong>
            <c:choose>
                <c:when test="${not empty taProfile.cvFilePath}">
                    <p class="text-muted">${taProfile.cvFilePath}</p>
                </c:when>
                <c:otherwise>
                    <p class="text-muted">CV not uploaded.</p>
                </c:otherwise>
            </c:choose>
        </div>
        <c:if test="${not empty taProfile.cvFilePath}">
            <a class="btn" href="${pageContext.request.contextPath}/cv-file?taUserId=${applicant.id}&jobId=${job.id}" target="_blank">View / Download CV</a>
        </c:if>
    </div>

    <div class="button-row top-gap">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/mo/manage-applicants?jobId=${job.id}">Back to Applicants</a>
    </div>
</section>

<%@ include file="common/footer.jspf" %>
