package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public abstract class AbstractStorage<SK> implements Storage {

    protected abstract int getSize();

    protected abstract Resume getResume(SK searchKey);

    protected abstract void updateResume(SK searchKey, Resume resume);

    protected abstract void saveResume(Resume resume, SK searchKey);

    protected abstract List<Resume> getAllResumes();

    protected abstract void deleteResume(SK searchKey);

    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
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
        List<Resume> resumes = getAllResumes();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    @Override
    public int size() {
        return getSize();
    }
}
