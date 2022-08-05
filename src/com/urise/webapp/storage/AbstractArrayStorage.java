package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void deleteElement(int index);

    private boolean isExist(int index) {
            return index>=0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(String uuid, Resume r) {
        int index = findIndex(uuid);
        if (!isExist(index)){
            throw new NotExistStorageException(uuid);
        } else {
            storage[index]=r;
        }
    }

    @Override
    public void save(Resume r) {
        int index= findIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is full",r.getUuid());
        } else if (isExist(index)){
            throw new ExistStorageException(r.getUuid());
        } else {
            insertElement(r,-(index+1));
            size++;
        }
    }

    @Override
    public Resume get(String uuid){
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid){
        int index= findIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElement(index);
            size--;
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }
}
