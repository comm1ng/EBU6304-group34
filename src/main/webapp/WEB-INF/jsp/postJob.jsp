<%@ include file="common/header.jspf" %>

<section class="card max-720">
    <h1>Post New Job</h1>
    <p class="muted">Create a TA opportunity for your module or activity.</p>

    <form method="post" action="${pageContext.request.contextPath}/mo/post-job" class="form-grid">
        <label for="title">Job Title</label>
        <input id="title" name="title" type="text" value="${title}" required>

        <label for="description">Description</label>
        <textarea id="description" name="description" rows="4" required>${description}</textarea>

        <label for="requiredSkills">Required Skills (comma separated)</label>
        <input id="requiredSkills" name="requiredSkills" type="text" value="${requiredSkills}" placeholder="Java, Communication">

        <label for="hoursPerWeek">Hours per Week</label>
        <input id="hoursPerWeek" name="hoursPerWeek" type="number" min="1" value="${hoursPerWeek}" required>

        <div class="button-row">
            <button class="btn" type="submit">Post Job</button>
            <a class="btn btn-outline" href="${pageContext.request.contextPath}/mo/dashboard">Back</a>
        </div>
    </form>
</section>

<%@ include file="common/footer.jspf" %>
