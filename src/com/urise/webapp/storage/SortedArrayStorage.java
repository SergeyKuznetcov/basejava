package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{
    @Override
    protected int findIndex(String uuid) {
        return Arrays.binarySearch(storage,0,size,new Resume(uuid));
    }

    @Override
    public void update(String uuid, Resume r) {
        int index = findIndex(uuid);
        if (index < 0){
            System.out.println(uuid + " doesn\'t exist");
        } else {
            delete(uuid);
            save(r);
        }
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
            return;
        }
        int index = findIndex((r.getUuid()));
        if (index > -1) {
            System.out.println(r + " already exists");
        } else {
            index = -(index+1);
            if (index==size){
                storage[size] = r;
            }else {
                for (int i = size; i > index; i--) {
                    storage[i] = storage[i-1];
                }
                storage[index]=r;
            }
            size++;
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println(uuid + " doesn\'t exist");
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println(uuid + " doesn\'t exist");
        } else {
            storage[index]=null;
            for (int i = index; i < size; i++) {
                storage[i]=storage[i+1];
            }
            size--;
            storage[size] = null;
        }
    }
}
