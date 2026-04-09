package com.example.tarecruitment.util;

import java.util.List;

public final class IdGenerator {
    private IdGenerator() {
    }

    public static String nextId(String prefix, List<String> existingIds) {
        int max = 0;
        if (existingIds != null) {
            for (String id : existingIds) {
                if (id != null && id.startsWith(prefix)) {
                    String numberPart = id.substring(prefix.length()).replaceAll("[^0-9]", "");
                    if (!numberPart.isBlank()) {
                        int value = Integer.parseInt(numberPart);
                        if (value > max) {
                            max = value;
                        }
                    }
                }
            }
        }
        return String.format("%s%03d", prefix, max + 1);
    }
}
