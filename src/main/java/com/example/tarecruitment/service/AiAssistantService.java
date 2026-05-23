package com.example.tarecruitment.service;

import com.example.tarecruitment.model.AiRecommendation;
import com.example.tarecruitment.model.Job;
import com.example.tarecruitment.model.JobApplication;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AiAssistantService {
    private static final int DEFAULT_LIMIT = 5;

    private final AiChatClient chatClient;
    private final Gson gson = new Gson();

    public AiAssistantService(AiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public List<AiRecommendation> recommendJobsForTa(TAProfile profile, List<Job> openJobs, int limit) {
        return recommendJobsForTa(profile, "", openJobs, limit);
    }

    public List<AiRecommendation> recommendJobsForTa(TAProfile profile, String resumeText, List<Job> openJobs, int limit) {
        int safeLimit = normalizeLimit(limit);
        List<AiRecommendation> fallback = rankJobsByRules(profile, openJobs, safeLimit);
        if (chatClient == null || !chatClient.isAvailable() || openJobs == null || openJobs.isEmpty()) {
            return fallback;
        }

        String response = callAi(buildJobSystemPrompt(), buildJobUserPrompt(profile, resumeText, openJobs, safeLimit));
        List<AiRecommendation> aiRecommendations = parseRecommendations(response, "jobId", "title");
        return aiRecommendations.isEmpty() ? fallback : keepKnownTargets(aiRecommendations, openJobs.stream()
                .map(Job::getId)
                .collect(Collectors.toSet()), safeLimit);
    }

    public List<AiRecommendation> recommendApplicantsForJob(Job job,
                                                             List<JobApplication> applications,
                                                             Map<String, User> taById,
                                                             Map<String, TAProfile> profileByUserId,
                                                             int limit) {
        return recommendApplicantsForJob(job, applications, taById, profileByUserId, Map.of(), limit);
    }

    public List<AiRecommendation> recommendApplicantsForJob(Job job,
                                                             List<JobApplication> applications,
                                                             Map<String, User> taById,
                                                             Map<String, TAProfile> profileByUserId,
                                                             Map<String, String> resumeTextByUserId,
                                                             int limit) {
        int safeLimit = normalizeLimit(limit);
        List<AiRecommendation> fallback = rankApplicantsByRules(job, applications, taById, profileByUserId, safeLimit);
        if (chatClient == null || !chatClient.isAvailable() || applications == null || applications.isEmpty()) {
            return fallback;
        }

        String response = callAi(buildApplicantSystemPrompt(),
                buildApplicantUserPrompt(job, applications, taById, profileByUserId, resumeTextByUserId, safeLimit));
        List<AiRecommendation> aiRecommendations = parseRecommendations(response, "taUserId", "name");
        return aiRecommendations.isEmpty() ? fallback : keepKnownTargets(aiRecommendations, applications.stream()
                .map(JobApplication::getTaUserId)
                .collect(Collectors.toSet()), safeLimit);
    }

    String buildJobUserPrompt(TAProfile profile, List<Job> openJobs, int limit) {
        return buildJobUserPrompt(profile, "", openJobs, limit);
    }

    String buildJobUserPrompt(TAProfile profile, String resumeText, List<Job> openJobs, int limit) {
        JsonObject payload = new JsonObject();
        payload.add("taProfile", gson.toJsonTree(profile));
        payload.addProperty("resumeText", resumeText == null ? "" : resumeText);
        payload.addProperty("limit", limit);
        payload.add("openJobs", gson.toJsonTree(openJobs));
        return gson.toJson(payload);
    }

    String buildApplicantUserPrompt(Job job,
                                    List<JobApplication> applications,
                                    Map<String, User> taById,
                                    Map<String, TAProfile> profileByUserId,
                                    int limit) {
        return buildApplicantUserPrompt(job, applications, taById, profileByUserId, Map.of(), limit);
    }

    String buildApplicantUserPrompt(Job job,
                                    List<JobApplication> applications,
                                    Map<String, User> taById,
                                    Map<String, TAProfile> profileByUserId,
                                    Map<String, String> resumeTextByUserId,
                                    int limit) {
        JsonObject payload = new JsonObject();
        payload.add("job", gson.toJsonTree(job));
        payload.addProperty("limit", limit);

        JsonArray candidates = new JsonArray();
        for (JobApplication application : safeList(applications)) {
            JsonObject candidate = new JsonObject();
            User user = taById == null ? null : taById.get(application.getTaUserId());
            candidate.addProperty("taUserId", application.getTaUserId());
            candidate.addProperty("applicationId", application.getId());
            candidate.addProperty("status", application.getStatus() == null ? "" : application.getStatus().name());
            candidate.addProperty("appliedAt", application.getAppliedAt());
            candidate.add("user", gson.toJsonTree(user));
            candidate.add("profile", gson.toJsonTree(profileByUserId == null ? null : profileByUserId.get(application.getTaUserId())));
            candidate.addProperty("resumeText", resumeTextByUserId == null ? "" : resumeTextByUserId.getOrDefault(application.getTaUserId(), ""));
            candidates.add(candidate);
        }
        payload.add("candidates", candidates);
        return gson.toJson(payload);
    }

    private List<AiRecommendation> rankJobsByRules(TAProfile profile, List<Job> openJobs, int limit) {
        return safeList(openJobs).stream()
                .map(job -> scoreJob(profile, job))
                .sorted(Comparator.comparingInt(AiRecommendation::getScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private AiRecommendation scoreJob(TAProfile profile, Job job) {
        Set<String> taSkills = normalizedSet(profile == null ? List.of() : profile.getSkills());
        Set<String> jobSkills = normalizedSet(job.getRequiredSkills());
        int overlap = 0;
        for (String skill : jobSkills) {
            if (taSkills.contains(skill)) {
                overlap++;
            }
        }

        int score = Math.min(75, overlap * 25);
        List<String> reasons = new ArrayList<>();
        if (overlap > 0) {
            reasons.add("Skills overlap with " + overlap + " required skill(s).");
        }
        if (containsText(job.getDescription(), profile == null ? "" : profile.getMajor())) {
            score += 10;
            reasons.add("Job description is related to the TA major.");
        }
        if (containsAny(job.getDescription(), profile == null ? "" : profile.getCvSummary())
                || containsAny(job.getDescription(), profile == null ? "" : profile.getExperience())) {
            score += 10;
            reasons.add("CV summary or experience appears relevant.");
        }
        if (job.getHoursPerWeek() > 0 && job.getHoursPerWeek() <= 8) {
            score += 5;
            reasons.add("Weekly workload is moderate.");
        }
        if (reasons.isEmpty()) {
            reasons.add("No exact skill overlap found; review manually before applying.");
        }

        return new AiRecommendation(job.getId(), job.getTitle(), clamp(score), reasons, "Rule-based recommendation");
    }

    private List<AiRecommendation> rankApplicantsByRules(Job job,
                                                          List<JobApplication> applications,
                                                          Map<String, User> taById,
                                                          Map<String, TAProfile> profileByUserId,
                                                          int limit) {
        return safeList(applications).stream()
                .map(application -> scoreApplicant(job, application, taById, profileByUserId))
                .sorted(Comparator.comparingInt(AiRecommendation::getScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private AiRecommendation scoreApplicant(Job job,
                                             JobApplication application,
                                             Map<String, User> taById,
                                             Map<String, TAProfile> profileByUserId) {
        TAProfile profile = profileByUserId == null ? null : profileByUserId.get(application.getTaUserId());
        User user = taById == null ? null : taById.get(application.getTaUserId());

        Set<String> requiredSkills = normalizedSet(job.getRequiredSkills());
        Set<String> taSkills = normalizedSet(profile == null ? List.of() : profile.getSkills());
        int overlap = 0;
        for (String skill : requiredSkills) {
            if (taSkills.contains(skill)) {
                overlap++;
            }
        }

        int score = Math.min(80, overlap * 25);
        List<String> reasons = new ArrayList<>();
        if (overlap > 0) {
            reasons.add("Matches " + overlap + " required skill(s).");
        }
        if (profile != null && containsText(job.getDescription(), profile.getMajor())) {
            score += 10;
            reasons.add("Academic background is relevant to the job.");
        }
        if (profile != null && (containsAny(job.getDescription(), profile.getCvSummary())
                || containsAny(job.getDescription(), profile.getExperience()))) {
            score += 10;
            reasons.add("CV or experience contains relevant context.");
        }
        if (reasons.isEmpty()) {
            reasons.add("Limited profile match; inspect CV before deciding.");
        }

        String title = user == null ? application.getTaUserId() : user.getFullName();
        return new AiRecommendation(application.getTaUserId(), title, clamp(score), reasons, "Rule-based recommendation");
    }

    private String callAi(String systemPrompt, String userPrompt) {
        try {
            return chatClient.complete(systemPrompt, userPrompt);
        } catch (RuntimeException ex) {
            return "";
        }
    }

    private List<AiRecommendation> parseRecommendations(String response, String idField, String titleField) {
        if (response == null || response.isBlank()) {
            return List.of();
        }
        try {
            JsonElement root = JsonParser.parseString(extractJsonArray(response));
            if (!root.isJsonArray()) {
                return List.of();
            }
            List<AiRecommendation> recommendations = new ArrayList<>();
            for (JsonElement element : root.getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();
                String targetId = getString(object, idField);
                if (targetId.isBlank()) {
                    targetId = getString(object, "targetId");
                }
                if (targetId.isBlank()) {
                    continue;
                }

                String title = getString(object, titleField);
                if (title.isBlank()) {
                    title = getString(object, "title");
                }
                int score = object.has("score") ? object.get("score").getAsInt() : 0;
                List<String> reasons = new ArrayList<>();
                if (object.has("reasons") && object.get("reasons").isJsonArray()) {
                    for (JsonElement reason : object.getAsJsonArray("reasons")) {
                        reasons.add(reason.getAsString());
                    }
                }
                String note = getString(object, "note");
                recommendations.add(new AiRecommendation(targetId, title, clamp(score), reasons, note.isBlank() ? "AI recommendation" : note));
            }
            return recommendations;
        } catch (RuntimeException ex) {
            return List.of();
        }
    }

    private List<AiRecommendation> keepKnownTargets(List<AiRecommendation> recommendations, Set<String> knownIds, int limit) {
        return recommendations.stream()
                .filter(recommendation -> knownIds.contains(recommendation.getTargetId()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private String buildJobSystemPrompt() {
        return "You are an assistant for a teaching assistant recruitment platform. "
                + "Recommend open jobs for a TA by considering profile, skills, experience, CV summary, uploaded resume text, workload, mode, and deadline. Return only a JSON array. "
                + "Each item must contain jobId, title, score from 0 to 100, reasons array, and note.";
    }

    private String buildApplicantSystemPrompt() {
        return "You are an assistant for module organisers hiring teaching assistants. "
                + "Recommend applicants for the selected job by considering required skills, profile, academic background, CV summary, uploaded PDF resume text, prior experience, and application status. Return only a JSON array. "
                + "Each item must contain taUserId, name, score from 0 to 100, reasons array, and note.";
    }

    private String extractJsonArray(String response) {
        int start = response.indexOf('[');
        int end = response.lastIndexOf(']');
        if (start >= 0 && end > start) {
            return response.substring(start, end + 1);
        }
        return response;
    }

    private String getString(JsonObject object, String field) {
        return object.has(field) && !object.get(field).isJsonNull() ? object.get(field).getAsString() : "";
    }

    private Set<String> normalizedSet(List<String> values) {
        Set<String> normalized = new HashSet<>();
        for (String value : safeList(values)) {
            String item = normalize(value);
            if (!item.isBlank()) {
                normalized.add(item);
            }
        }
        return normalized;
    }

    private boolean containsText(String source, String query) {
        String normalizedQuery = normalize(query);
        return !normalizedQuery.isBlank() && normalize(source).contains(normalizedQuery);
    }

    private boolean containsAny(String source, String text) {
        String normalizedSource = normalize(source);
        for (String token : normalize(text).split("\\s+")) {
            if (token.length() >= 4 && normalizedSource.contains(token)) {
                return true;
            }
        }
        return false;
    }

    private String normalize(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT).trim();
    }

    private int normalizeLimit(int limit) {
        return limit <= 0 ? DEFAULT_LIMIT : Math.min(limit, 10);
    }

    private int clamp(int score) {
        return Math.max(0, Math.min(100, score));
    }

    private <T> List<T> safeList(List<T> values) {
        return values == null ? List.of() : values;
    }
}
