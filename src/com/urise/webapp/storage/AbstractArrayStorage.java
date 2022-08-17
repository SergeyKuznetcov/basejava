package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void insertElement(Resume r, int index);

    protected abstract void deleteElement(int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected int getSize() {
        return size;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            insertElement(resume, -((Integer) searchKey + 1));
            size++;
        }
    }

    @Override
    protected List<Resume> getAllResumes() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected void deleteResume(Object searchKey) {
        deleteElement((Integer) searchKey);
        size--;
    }
}
