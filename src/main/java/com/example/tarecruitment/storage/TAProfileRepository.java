package com.example.tarecruitment.storage;

import com.example.tarecruitment.model.TAProfile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TAProfileRepository {
    private final Path path;
    private final JsonDataManager dataManager;

    public TAProfileRepository(Path path, JsonDataManager dataManager) {
        this.path = path;
        this.dataManager = dataManager;
    }

    public List<TAProfile> findAll() {
        return new ArrayList<>(dataManager.readList(path, TAProfile.class));
    }

    public Optional<TAProfile> findByUserId(String userId) {
        return findAll().stream().filter(p -> p.getUserId().equals(userId)).findFirst();
    }

    public void saveAll(List<TAProfile> profiles) {
        dataManager.writeList(path, profiles);
    }

    public void add(TAProfile profile) {
        List<TAProfile> profiles = findAll();
        profiles.add(profile);
        saveAll(profiles);
    }

    public void upsert(TAProfile profile) {
        List<TAProfile> profiles = findAll();
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
