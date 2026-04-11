<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="page-head">
    <h2>Profile Management</h2>
    <p class="text-muted">Maintain identity information and your role-specific profile details.</p>
</section>

<section class="card max-card">
    <form method="post" action="${pageContext.request.contextPath}/profile" class="form-grid" enctype="multipart/form-data">
        <h3>Basic Information</h3>

        <label for="fullName">Full Name</label>
        <input id="fullName" name="fullName" type="text" value="${currentUser.fullName}" required>

        <label for="email">Email</label>
        <input id="email" name="email" type="email" value="${currentUser.email}" required>

        <c:if test="${activeRole == 'TA'}">
            <h3>TA Profile</h3>

            <label for="major">Major</label>
            <input id="major" name="major" type="text" value="${taProfile.major}">

            <label for="academicYear">Academic Year</label>
            <input id="academicYear" name="academicYear" type="text" value="${taProfile.academicYear}" placeholder="Year 2 / Year 3">

            <label for="skills">Skills (comma separated)</label>
            <input id="skills" name="skills" type="text" value="${fn:join(taProfile.skills, ', ')}">

            <label for="cvFilePath">CV File Path (Auto-generated after upload)</label>
            <input id="cvFilePath" type="text" value="${taProfile.cvFilePath}" readonly>

            <label for="cvFile">Upload CV File (PDF/DOC/DOCX)</label>
            <input id="cvFile" name="cvFile" type="file" accept=".pdf,.doc,.docx">

            <c:if test="${not empty taProfile.cvFilePath}">
                <p class="cell-sub">Current CV: ${taProfile.cvFilePath}</p>
                <a href="${pageContext.request.contextPath}/cv-file?taUserId=${currentUser.id}" target="_blank">View / Download Current CV</a>
            </c:if>

            <label for="cvSummary">CV Summary</label>
            <textarea id="cvSummary" name="cvSummary" rows="3">${taProfile.cvSummary}</textarea>

            <label for="experience">Experience</label>
            <textarea id="experience" name="experience" rows="3">${taProfile.experience}</textarea>
        </c:if>

        <c:if test="${activeRole == 'MO'}">
            <h3>MO Profile</h3>

            <label for="workUnit">Work Unit / Organisation</label>
            <input id="workUnit" name="workUnit" type="text" value="${moProfile.workUnit}" required>

            <label for="title">Position Title</label>
            <input id="title" name="title" type="text" value="${moProfile.title}">

            <label for="bio">Bio</label>
            <textarea id="bio" name="bio" rows="4">${moProfile.bio}</textarea>
        </c:if>

        <button class="btn" type="submit">Save Profile</button>
    </form>
</section>

<%@ include file="common/footer.jspf" %>

