package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
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
    public abstract void save(Resume r);

    public Resume get(String uuid){
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println(uuid + " doesn\'t exist");
            return null;
        }
        return storage[index];
    }

    @Override
    public abstract void delete(String uuid);

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }
}
