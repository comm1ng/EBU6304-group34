package com.example.tarecruitment.service;

import com.example.tarecruitment.model.TAProfile;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResumeTextServiceTest {
    @Test
    void extractResumeText_ShouldReadUploadedResumeInsideDataDirectory() throws Exception {
        Path dataDir = Files.createTempDirectory("resume-text-test");
        Path cvDir = dataDir.resolve("uploads/cv");
        Files.createDirectories(cvDir);
        Files.writeString(cvDir.resolve("alice.pdf"),
                "Fake PDF resume. Java servlet lab support and data structures tutoring.");

        ResumeTextService service = new ResumeTextService(dataDir);
        TAProfile profile = new TAProfile("U001", "Computer Science", "Year 3",
                List.of("Java"), "uploads/cv/alice.pdf", "", "");

        String text = service.extractResumeText(profile);

        assertTrue(text.contains("Java servlet lab support"));
        assertTrue(text.contains("data structures tutoring"));
    }

    @Test
    void extractResumeText_ShouldRejectPathTraversal() throws Exception {
        Path dataDir = Files.createTempDirectory("resume-text-test");
        Path outsideFile = Files.createTempFile("outside-cv", ".pdf");
        Files.writeString(outsideFile, "This should not be read.");

        ResumeTextService service = new ResumeTextService(dataDir);
        TAProfile profile = new TAProfile("U001", "Computer Science", "Year 3",
                List.of("Java"), "../" + outsideFile.getFileName(), "", "");

        assertEquals("", service.extractResumeText(profile));
    }
}
