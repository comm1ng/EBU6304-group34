package com.example.tarecruitment.servlet;

import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.util.ValidationUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "updateJobStatusServlet", urlPatterns = {"/mo/update-job-status"})
public class UpdateJobStatusServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = requireRole(request, response, Role.MO);
        if (user == null) {
            return;
        }

        String jobId = ValidationUtil.safeTrim(request.getParameter("jobId"));
        String statusText = ValidationUtil.safeTrim(request.getParameter("status"));

        try {
            Job job = container().getJobService().getById(jobId)
                    .orElseThrow(() -> new IllegalArgumentException("Job not found."));

            if (!user.getId().equals(job.getPostedByUserId())) {
                throw new IllegalArgumentException("You can only update jobs posted by you.");
            }

            JobStatus status = JobStatus.valueOf(statusText);
            container().getJobService().updateJobStatus(jobId, status);
            setFlashSuccess(request, "Job status updated to " + status + ".");
        } catch (IllegalArgumentException ex) {
            setFlashError(request, ex.getMessage());
        }

        redirect(response, request, "/mo/manage-jobs");
    }
}

