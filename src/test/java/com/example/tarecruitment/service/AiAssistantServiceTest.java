package com.example.tarecruitment.service;

import com.example.tarecruitment.model.AiRecommendation;
import com.example.tarecruitment.model.ApplicationStatus;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.JobStatus;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AiAssistantServiceTest {
    @Test
    void recommendJobsForTa_WithoutApi_ShouldUseRuleBasedRanking() {
        AiAssistantService service = new AiAssistantService(new StubChatClient(false, ""));
        TAProfile profile = new TAProfile("U001", "Computer Science", "Year 3",
                List.of("Java", "Python"), "", "Built Java web apps", "Tutor for programming labs");

        List<Job> jobs = List.of(
                job("J001", "Programming TA", "Support Java programming labs", List.of("Java", "Servlet"), 6),
                job("J002", "Writing TA", "Help with academic writing", List.of("Writing"), 4)
        );

        List<AiRecommendation> recommendations = service.recommendJobsForTa(profile, jobs, 2);

        assertEquals(2, recommendations.size());
        assertEquals("J001", recommendations.get(0).getTargetId());
        assertTrue(recommendations.get(0).getScore() > recommendations.get(1).getScore());
        assertEquals("Rule-based recommendation", recommendations.get(0).getNote());
    }

    @Test
    void recommendJobsForTa_WithApiJson_ShouldUseAiRecommendations() {
        String aiJson = """
                [
                  {"jobId":"J002","title":"Writing TA","score":88,"reasons":["Strong communication fit"],"note":"AI ranked"}
                ]
                """;
        AiAssistantService service = new AiAssistantService(new StubChatClient(true, aiJson));

        List<AiRecommendation> recommendations = service.recommendJobsForTa(
                new TAProfile("U001", "", "", List.of("Java"), "", "", ""),
                List.of(job("J001", "Programming TA", "Java", List.of("Java"), 6),
                        job("J002", "Writing TA", "Writing", List.of("Writing"), 4)),
                5
        );

        assertEquals(1, recommendations.size());
        assertEquals("J002", recommendations.get(0).getTargetId());
        assertEquals("AI ranked", recommendations.get(0).getNote());
    }

    @Test
    void recommendApplicantsForJob_WhenApiFails_ShouldFallbackToRules() {
        AiAssistantService service = new AiAssistantService(new FailingChatClient());
        Job job = job("J001", "Java TA", "Support Java labs", List.of("Java"), 6);
        JobApplication application = new JobApplication("A001", "J001", "U001",
                ApplicationStatus.PENDING, "2026-04-01T10:00:00", null, null);
        User user = new User("U001", "ta1", "123456", "TA One", "ta1@example.com", List.of(), List.of());
        TAProfile profile = new TAProfile("U001", "Computer Science", "Year 3",
                List.of("Java"), "", "Java servlet project", "");

        List<AiRecommendation> recommendations = service.recommendApplicantsForJob(
                job,
                List.of(application),
                Map.of("U001", user),
                Map.of("U001", profile),
                5
        );

        assertEquals(1, recommendations.size());
        assertEquals("U001", recommendations.get(0).getTargetId());
        assertEquals("TA One", recommendations.get(0).getTitle());
        assertFalse(recommendations.get(0).getReasons().isEmpty());
    }

    @Test
    void buildPrompt_ShouldIncludeProfileAndJobs() {
        AiAssistantService service = new AiAssistantService(new StubChatClient(false, ""));
        String prompt = service.buildJobUserPrompt(
                new TAProfile("U001", "AI", "Year 4", List.of("Python"), "", "", ""),
                "Uploaded resume text mentions Python tutoring and lab support.",
                List.of(job("J001", "AI TA", "Help AI labs", List.of("Python"), 5)),
                3
        );

        assertTrue(prompt.contains("U001"));
        assertTrue(prompt.contains("AI TA"));
        assertTrue(prompt.contains("Python tutoring"));
        assertTrue(prompt.contains("\"limit\":3"));
    }

    @Test
    void buildApplicantPrompt_ShouldIncludeUploadedResumeText() {
        AiAssistantService service = new AiAssistantService(new StubChatClient(false, ""));
        JobApplication application = new JobApplication("A001", "J001", "U001",
                ApplicationStatus.PENDING, "2026-04-01T10:00:00", null, null);
        User user = new User("U001", "ta1", "123456", "TA One", "ta1@example.com", List.of(), List.of());
        TAProfile profile = new TAProfile("U001", "Computer Science", "Year 3",
                List.of("Java"), "uploads/cv/u001.pdf", "Java lab support", "");

        String prompt = service.buildApplicantUserPrompt(
                job("J001", "Java TA", "Support Java labs", List.of("Java"), 6),
                List.of(application),
                Map.of("U001", user),
                Map.of("U001", profile),
                Map.of("U001", "Uploaded PDF resume: servlet coursework reviewer and Java tutor."),
                5
        );

        assertTrue(prompt.contains("Uploaded PDF resume"));
        assertTrue(prompt.contains("servlet coursework reviewer"));
        assertTrue(prompt.contains("TA One"));
    }

    private Job job(String id, String title, String description, List<String> skills, int hoursPerWeek) {
        return new Job(id, title, description, skills, hoursPerWeek,
                "2026-05-30", "Hybrid", "MO001", JobStatus.OPEN, "2026-04-01T10:00:00");
    }

    private static class StubChatClient implements AiChatClient {
        private final boolean available;
        private final String response;

        private StubChatClient(boolean available, String response) {
            this.available = available;
            this.response = response;
        }

        @Override
        public boolean isAvailable() {
            return available;
        }

        @Override
        public String complete(String systemPrompt, String userPrompt) {
            return response;
        }
    }

    private static class FailingChatClient implements AiChatClient {
        @Override
        public boolean isAvailable() {
            return true;
        }

        @Override
        public String complete(String systemPrompt, String userPrompt) {
            throw new IllegalStateException("network unavailable");
        }
    }
}
