package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected Object findSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry :
                storage.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }
}
