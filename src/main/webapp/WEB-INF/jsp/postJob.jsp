<%@ include file="common/header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="page-head">
    <h2>Post New Job</h2>
    <p class="text-muted">Create a realistic recruitment posting for TA candidates.</p>
</section>

<section class="card max-card">
    <form method="post" action="${pageContext.request.contextPath}/mo/post-job" class="form-grid">
        <label for="title">Job Title</label>
        <input id="title" name="title" type="text" value="${title}" required>

        <label for="description">Job Description</label>
        <textarea id="description" name="description" rows="4" required>${description}</textarea>

        <label for="requiredSkills">Required Skills (comma separated)</label>
        <input id="requiredSkills" name="requiredSkills" type="text" value="${requiredSkills}" placeholder="Java, communication, classroom support">

        <label for="hoursPerWeek">Hours per Week</label>
        <input id="hoursPerWeek" name="hoursPerWeek" type="number" min="1" value="${hoursPerWeek}" required>

        <label for="deadline">Application Deadline</label>
        <input id="deadline" name="deadline" type="date" value="${deadline}">

        <label for="locationMode">Location / Mode</label>
        <select id="locationMode" name="locationMode">
            <option value="">Select mode</option>
            <option value="On-campus" <c:if test="${locationMode == 'On-campus'}">selected</c:if>>On-campus</option>
            <option value="Online" <c:if test="${locationMode == 'Online'}">selected</c:if>>Online</option>
            <option value="Hybrid" <c:if test="${locationMode == 'Hybrid'}">selected</c:if>>Hybrid</option>
        </select>

        <div class="button-row">
            <button class="btn" type="submit">Publish Job</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/mo/dashboard">Back</a>
        </div>
    </form>
</section>

<%@ include file="common/footer.jspf" %>

