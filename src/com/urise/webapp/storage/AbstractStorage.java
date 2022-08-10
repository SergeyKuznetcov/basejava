package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void clearStorage();

    protected abstract int getSize();

    protected abstract Resume getResume(int index);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract Resume[] getAllResumes();

    protected abstract void deleteResume(int index);

    protected abstract int findSearchKey(String uuid);

    private int getNotExistingSearchKey(String uuid) {
        int index = findSearchKey(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private int getExistingSearchKey(String uuid) {
        int index = findSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    @Override
    public void clear() {
        clearStorage();
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
    public Resume[] getAll() {
        return getAllResumes();
    }

    @Override
    public int size() {
        return getSize();
    }
}
