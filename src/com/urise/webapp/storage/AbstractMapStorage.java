package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractMapStorage extends AbstractStorage{
    protected final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected int getSize() {
        return storage.size();
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage.replace((String) searchKey, resume);
    }

    @Override
    protected Resume[] getAllResumes() {
        ArrayList<Resume> resumes = new ArrayList<>(storage.values());
        Resume[] result = new Resume[storage.size()];
        for (int i = 0; i < resumes.size(); i++) {
            result[i] = resumes.get(i);
        }
        return result;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }
}
