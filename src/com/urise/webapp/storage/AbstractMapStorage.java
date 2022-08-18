package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected int getSize() {
        return storage.size();
    }

    @Override
    protected Resume getResume(SK searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected List<Resume> getAllResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void deleteResume(SK searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void saveResume(Resume resume, SK searchKey) {
        storage.put(resume.getUuid(), resume);
    }
}
