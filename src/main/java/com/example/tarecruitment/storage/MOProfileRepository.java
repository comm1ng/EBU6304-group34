package com.example.tarecruitment.storage;

import com.example.tarecruitment.model.MOProfile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MOProfileRepository {
    private final Path path;
    private final JsonDataManager dataManager;

    public MOProfileRepository(Path path, JsonDataManager dataManager) {
        this.path = path;
        this.dataManager = dataManager;
    }

    public List<MOProfile> findAll() {
        return new ArrayList<>(dataManager.readList(path, MOProfile.class));
    }

    public Optional<MOProfile> findByUserId(String userId) {
        return findAll().stream().filter(p -> p.getUserId().equals(userId)).findFirst();
    }

    public void saveAll(List<MOProfile> profiles) {
        dataManager.writeList(path, profiles);
    }

    public void upsert(MOProfile profile) {
        List<MOProfile> profiles = findAll();
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getUserId().equals(profile.getUserId())) {
                profiles.set(i, profile);
                saveAll(profiles);
                return;
            }
        }
        profiles.add(profile);
        saveAll(profiles);
    }
}
