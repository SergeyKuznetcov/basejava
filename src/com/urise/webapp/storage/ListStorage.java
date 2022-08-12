package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage{
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected Object findSearchKey(String uuid){
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())){
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return ((Integer) searchKey) >= 0;
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected int getSize() {
        return storage.size();
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey,resume);
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Resume[] getAllResumes() {
        Resume[] resumes = new Resume[storage.size()];
        for (int i = 0; i < storage.size(); i++) {
            resumes[i]=storage.get(i);
        }
        return resumes;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove((int) searchKey);
    }
}
