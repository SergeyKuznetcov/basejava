package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    protected abstract void clearStorage();
    protected abstract int getSize();
    protected abstract Resume getResume(int index);
    protected abstract void updateResume(int index, Resume resume);
    protected abstract void saveResume(Resume resume,int index);
    protected abstract Resume[] getAllResumes();
    protected abstract void deleteResume(int index);
    protected abstract int findIndex(String uuid);

    @Override
    public void clear() {
        clearStorage();
    }

    @Override
    public void update(String uuid, Resume r) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            updateResume(index,r);
        }
    }

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index > -1){
            throw new ExistStorageException(r.getUuid());
        }else {
            saveResume(r,index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0){
            throw new NotExistStorageException(uuid);
        }else {
            return getResume(index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index<0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
        }
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
