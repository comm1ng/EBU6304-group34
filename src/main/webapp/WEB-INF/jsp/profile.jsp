<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="profile-hero profile-edit-hero">
    <div class="profile-identity">
        <div class="profile-avatar" aria-hidden="true">${activeRole}</div>
        <div>
            <p class="profile-kicker">Profile Management</p>
            <h2>${currentUser.fullName}</h2>
            <p class="text-muted">${currentUser.email}</p>
        </div>
    </div>
    <div class="profile-hero-meta">
        <span class="badge badge-role">${activeRole}</span>
        <c:if test="${activeRole == 'TA'}">
            <span>${taProfile.major}</span>
            <span>${taProfile.academicYear}</span>
        </c:if>
        <c:if test="${activeRole == 'MO'}">
            <span>${moProfile.workUnit}</span>
            <span>${moProfile.title}</span>
        </c:if>
    </div>
</section>

<section class="card profile-edit-card">
    <form method="post" action="${pageContext.request.contextPath}/profile" enctype="multipart/form-data">
        <div class="profile-edit-section">
            <div class="profile-edit-section-head">
                <span>Account</span>
                <h3>Basic Information</h3>
                <p class="text-muted">Keep your account identity up to date.</p>
            </div>
            <div class="profile-edit-grid">
                <div class="field-group">
                    <label for="fullName">Full Name</label>
                    <input id="fullName" name="fullName" type="text" value="${currentUser.fullName}" required>
                </div>
                <div class="field-group">
                    <label for="email">Email</label>
                    <input id="email" name="email" type="email" value="${currentUser.email}" required>
                </div>
            </div>
        </div>

        <c:if test="${activeRole == 'TA'}">
            <div class="profile-edit-section">
                <div class="profile-edit-section-head">
                    <span>TA Profile</span>
                    <h3>Academic Fit</h3>
                    <p class="text-muted">These details help module organisers and the AI assistant evaluate fit.</p>
                </div>
                <div class="profile-edit-grid">
                    <div class="field-group">
                        <label for="major">Major</label>
                        <input id="major" name="major" type="text" value="${taProfile.major}">
                    </div>
                    <div class="field-group">
                        <label for="academicYear">Academic Year</label>
                        <input id="academicYear" name="academicYear" type="text" value="${taProfile.academicYear}" placeholder="Year 2 / Year 3">
                    </div>
                    <div class="field-group profile-edit-span">
                        <label for="skills">Skills</label>
                        <input id="skills" name="skills" type="text" value="${taSkillsText}" placeholder="Java, Python, Communication">
                    </div>
                </div>
            </div>

            <div class="profile-edit-section">
                <div class="profile-edit-section-head">
                    <span>Documents</span>
                    <h3>CV & Experience</h3>
                    <p class="text-muted">Upload a CV and summarize experience for recommendation matching.</p>
                </div>
                <div class="profile-edit-stack">
                    <div class="cv-upload-panel">
                        <div class="cv-current-block">
                            <strong>Current CV</strong>
                            <c:choose>
                                <c:when test="${not empty taProfile.cvFilePath}">
                                    <p class="text-muted">${taProfile.cvFilePath}</p>
                                    <a class="btn btn-secondary btn-small" href="${pageContext.request.contextPath}/cv-file?taUserId=${currentUser.id}" target="_blank">View / Download Current CV</a>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-muted">No CV has been uploaded yet.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="field-group">
                            <label for="cvFile">Upload CV File</label>
                            <input id="cvFile" name="cvFile" type="file" accept=".pdf,.doc,.docx">
                        </div>
                    </div>
                    <input id="cvFilePath" type="hidden" value="${taProfile.cvFilePath}" readonly>
                    <div class="profile-edit-grid">
                        <div class="field-group profile-edit-span">
                            <label for="cvSummary">CV Summary</label>
                            <textarea id="cvSummary" name="cvSummary" rows="4">${taProfile.cvSummary}</textarea>
                        </div>
                        <div class="field-group profile-edit-span">
                            <label for="experience">Experience</label>
                            <textarea id="experience" name="experience" rows="4">${taProfile.experience}</textarea>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${activeRole == 'MO'}">
            <div class="profile-edit-section">
                <div class="profile-edit-section-head">
                    <span>MO Profile</span>
                    <h3>Teaching Organisation</h3>
                    <p class="text-muted">These details appear in module organiser workflows.</p>
                </div>
                <div class="profile-edit-grid">
                    <div class="field-group">
                        <label for="workUnit">Work Unit / Organisation</label>
                        <input id="workUnit" name="workUnit" type="text" value="${moProfile.workUnit}" required>
                    </div>
                    <div class="field-group">
                        <label for="title">Position Title</label>
                        <input id="title" name="title" type="text" value="${moProfile.title}">
                    </div>
                    <div class="field-group profile-edit-span">
                        <label for="bio">Bio</label>
                        <textarea id="bio" name="bio" rows="4">${moProfile.bio}</textarea>
                    </div>
                </div>
            </div>
        </c:if>

        <div class="profile-edit-actions">
            <button class="btn" type="submit">Save Profile</button>
        </div>
    </form>
</section>

<%@ include file="common/footer.jspf" %>
