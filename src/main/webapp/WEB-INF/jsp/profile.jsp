<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-header">
    <h1>My Profile</h1>
    <p class="muted">Maintain your personal details and role-specific profile information.</p>
</section>

<section class="card max-820">
    <form method="post" action="${pageContext.request.contextPath}/profile" class="form-grid">
        <label for="fullName">Full Name</label>
        <input id="fullName" name="fullName" type="text" value="${currentUser.fullName}" required>

        <label for="email">Email</label>
        <input id="email" name="email" type="email" value="${currentUser.email}" required>

        <c:if test="${activeRole == 'TA'}">
            <label for="major">Major</label>
            <input id="major" name="major" type="text" value="${taProfile.major}">

            <label for="academicYear">Academic Year</label>
            <input id="academicYear" name="academicYear" type="text" value="${taProfile.academicYear}" placeholder="Year 2 / Year 3">

            <label for="skills">Skills (comma separated)</label>
            <input id="skills" name="skills" type="text" value="${taProfile.skills}">

            <label for="cvFilePath">CV File Path</label>
            <input id="cvFilePath" name="cvFilePath" type="text" value="${taProfile.cvFilePath}" placeholder="docs/cv/your_cv.pdf">

            <label for="cvSummary">CV Summary</label>
            <textarea id="cvSummary" name="cvSummary" rows="3">${taProfile.cvSummary}</textarea>

            <label for="experience">Experience</label>
            <textarea id="experience" name="experience" rows="3">${taProfile.experience}</textarea>
        </c:if>

        <c:if test="${activeRole == 'MO'}">
            <label for="workUnit">Work Unit / Organisation</label>
            <input id="workUnit" name="workUnit" type="text" value="${moProfile.workUnit}" required>

            <label for="title">Job Title</label>
            <input id="title" name="title" type="text" value="${moProfile.title}">

            <label for="bio">Bio / Description</label>
            <textarea id="bio" name="bio" rows="4">${moProfile.bio}</textarea>
        </c:if>

        <button class="btn" type="submit">Save Profile</button>
    </form>
</section>

<%@ include file="common/footer.jspf" %>
