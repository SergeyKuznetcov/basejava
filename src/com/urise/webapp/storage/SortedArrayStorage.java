package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected void insertElement(Resume r) {
        int index = -(findIndex(r.getUuid()) + 1);
        if (index == size) {
            storage[size] = r;
        } else {
            for (int i = size; i > index; i--) {
                storage[i] = storage[i - 1];
            }
            storage[index] = r;
        }
        size++;
    }

    @Override
    protected void deleteElement(int index) {
        storage[index] = null;
        for (int i = index; i < size; i++) {
            storage[i] = storage[i + 1];
        }
        size--;
        storage[size] = null;
    }

    @Override
    protected void updateElement(String uuid, Resume r) {
        deleteElement(findIndex(uuid));
        insertElement(r);
    }
}

