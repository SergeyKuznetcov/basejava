package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage{
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected int findSearchKey(String uuid){
        for (Resume r :
                storage) {
            if (r.getUuid().equals(uuid)){
                return storage.indexOf(r);
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
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
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.set(index,resume);
    }

    @Override
    protected void saveResume(Resume resume,int index) {
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
    protected void deleteResume(int index) {
        storage.remove(index);
    }
}
