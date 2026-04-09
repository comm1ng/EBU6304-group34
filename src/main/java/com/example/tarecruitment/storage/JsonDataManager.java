package com.example.tarecruitment.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonDataManager {
    private final Gson gson;

    public JsonDataManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public synchronized <T> List<T> readList(Path path, Class<T> clazz) {
        ensureFile(path);
        try {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            if (json == null || json.isBlank()) {
                return new ArrayList<>();
            }
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            List<T> data = gson.fromJson(json, listType);
            return data == null ? new ArrayList<>() : data;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read JSON file: " + path, ex);
        }
    }

    public synchronized <T> void writeList(Path path, List<T> data) {
        ensureFile(path);
        try {
            String json = gson.toJson(data == null ? new ArrayList<>() : data);
            Files.writeString(path, json, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to write JSON file: " + path, ex);
        }
    }

    private void ensureFile(Path path) {
        try {
            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(path)) {
                Files.writeString(path, "[]", StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to initialize JSON file: " + path, ex);
        }
    }
}
