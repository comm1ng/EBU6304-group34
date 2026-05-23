package com.example.tarecruitment.service;

import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.util.ValidationUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResumeTextService {
    private static final int MAX_RESUME_CHARS = 2500;

    private final Path dataDir;

    public ResumeTextService(Path dataDir) {
        this.dataDir = dataDir.toAbsolutePath().normalize();
    }

    public String extractResumeText(TAProfile profile) {
        if (profile == null) {
            return "";
        }
        String cvFilePath = ValidationUtil.safeTrim(profile.getCvFilePath());
        if (cvFilePath.isBlank()) {
            return "";
        }

        Path cvFile = dataDir.resolve(cvFilePath).normalize();
        if (!cvFile.startsWith(dataDir) || !Files.exists(cvFile) || !Files.isRegularFile(cvFile)) {
            return "";
        }

        try {
            byte[] bytes = Files.readAllBytes(cvFile);
            String text = new String(bytes, StandardCharsets.UTF_8)
                    .replaceAll("[^\\x09\\x0A\\x0D\\x20-\\x7E]", " ")
                    .replaceAll("\\s+", " ")
                    .trim();
            if (text.length() <= MAX_RESUME_CHARS) {
                return text;
            }
            return text.substring(0, MAX_RESUME_CHARS);
        } catch (IOException ex) {
            return "";
        }
    }
}
