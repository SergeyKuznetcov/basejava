package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {

    void update(String uuid, Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}
