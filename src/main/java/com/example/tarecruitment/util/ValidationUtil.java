package com.example.tarecruitment.util;

import java.util.regex.Pattern;

public final class ValidationUtil {
    private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private ValidationUtil() {
    }

    public static void requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }

    public static void requireMinLength(String value, int minLength, String fieldName) {
        requireNotBlank(value, fieldName);
        if (value.trim().length() < minLength) {
            throw new IllegalArgumentException(fieldName + " must be at least " + minLength + " characters.");
        }
    }

    public static void requireEmail(String email) {
        requireNotBlank(email, "Email");
        if (!SIMPLE_EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Email format is invalid.");
        }
    }

    public static int parsePositiveInteger(String value, String fieldName) {
        requireNotBlank(value, fieldName);
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed <= 0) {
                throw new IllegalArgumentException(fieldName + " must be greater than 0.");
            }
            return parsed;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " must be a valid number.");
        }
    }

    public static String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }
}
