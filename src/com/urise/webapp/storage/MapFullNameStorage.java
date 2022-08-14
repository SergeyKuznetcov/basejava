package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;

public class MapFullNameStorage extends AbstractMapStorage{

    @Override
    public void save(Resume r) {
        saveResume(r, getNotExistingSearchKey(r.getFullName()));
    }

    @Override
    protected Object findSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry :
                storage.entrySet()) {
            if (entry.getValue().getFullName().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        storage.put(resume.getFullName(), resume);
    }
}
