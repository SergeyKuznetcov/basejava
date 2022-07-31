package com.urise.webapp.storage;


import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume r) {
        storage[size] = r;
        size++;
    }

    @Override
    protected void deleteElement(int index) {
        size--;
        storage[index] = storage[size];
        storage[size] = null;
    }

    @Override
    protected void updateElement(String uuid, Resume r) {
        storage[findIndex(uuid)]=r;
    }
}
