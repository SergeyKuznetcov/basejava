package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    protected abstract void insertElement(Resume r);

    protected abstract void deleteElement(int index);

    protected abstract void updateElement(String uuid, Resume r);

    private boolean isExist(String uuid) {
        if (findIndex(uuid) < 0){
            return false;
        } else{
            return true;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(String uuid, Resume r) {
        if (!isExist(uuid)){
            System.out.println(uuid + " doesn\'t exist");
        } else {
            updateElement(uuid,r);
        }
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
            return;
        } else if (isExist(r.getUuid())){
            System.out.println(r + " already exists");
        } else {
            insertElement(r);
        }
    }

    @Override
    public Resume get(String uuid){
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println(uuid + " doesn\'t exist");
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid){
        if (!isExist(uuid)) {
            System.out.println(uuid + " doesn\'t exist");
        } else {
            deleteElement(findIndex(uuid));
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
