package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public abstract class AbstractStorage implements Storage {

    protected abstract int getSize();

    protected abstract Resume getResume(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract void saveResume(Resume resume, Object searchKey);

    protected abstract Resume[] getAllResumes();

    protected abstract void deleteResume(Object searchKey);

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public void update(String uuid, Resume r) {
        updateResume(getExistingSearchKey(uuid), r);
    }

    @Override
    public void save(Resume r) {
        saveResume(r, getNotExistingSearchKey(r.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return getResume(getExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(getExistingSearchKey(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        Collections.addAll(resumes, getAllResumes());
        resumes.sort((o1, o2) -> {
            int result = o1.getFullName().compareTo(o2.getFullName());
            if (result == 0){
                return o1.getUuid().compareTo(o2.getUuid());
            }
            return result;
        });
        return resumes;
    }

    @Override
    public int size() {
        return getSize();
    }
}
