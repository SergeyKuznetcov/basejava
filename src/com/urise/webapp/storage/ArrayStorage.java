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
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
            return;
        }
        if (findIndex(r.getUuid()) != -1) {
            System.out.println(r + " already exists");
        } else {
            storage[size] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println(uuid + " doesn\'t exist");
        } else {
            size--;
            storage[index] = storage[size];
            storage[size] = null;
        }
    }
}
