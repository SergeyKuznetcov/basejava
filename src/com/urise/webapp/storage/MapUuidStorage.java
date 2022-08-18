package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;

public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected void updateResume(String searchKey, Resume resume) {
        storage.replace(searchKey, resume);
    }

    @Override
    protected String findSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry :
                storage.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }
}
