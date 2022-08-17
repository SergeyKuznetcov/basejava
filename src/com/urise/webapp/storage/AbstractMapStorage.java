package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    protected List<Resume> getAllResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }
}
