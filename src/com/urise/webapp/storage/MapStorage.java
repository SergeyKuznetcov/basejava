package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final HashMap<String, Resume> storage = new HashMap<>();


    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected int getSize() {
        return storage.size();
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index + "");
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.replace(index + "", resume);
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        storage.put(resume.hashCode() + "", resume);
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
    protected void deleteResume(int index) {
        storage.remove(index + "");
    }

    @Override
    protected int findSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry :
                storage.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) {
                return Integer.parseInt(entry.getKey());
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey + "");
    }
}
