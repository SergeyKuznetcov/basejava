package com.urise.webapp.storage;


import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    private int resumeSearch(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                System.out.println(uuid + " was found");
                return i;
            }
        }
        System.out.println(uuid + " wasn\'t found");
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public void updateResume(String uuid) {
        int index = resumeSearch(uuid);
        if (index != -1) {
            //Do something
        }
    }

    public void save(Resume r) {
        if (resumeSearch(r.getUuid()) == -1) {
            if (size < 10000) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Storage is full");
            }
        }
    }

    public Resume get(String uuid) {
        int index = resumeSearch(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        int index = resumeSearch(uuid);
        if (index != -1) {
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
            storage[size] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        if (size == 0) {
            return new Resume[0];
        } else {
            return Arrays.copyOfRange(storage, 0, size);
        }
    }

    public int size() {
        return size;
    }
}
