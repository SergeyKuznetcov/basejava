package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MapFullNameStorageTest extends AbstractMapStorageTest{
    MapFullNameStorageTest() {
        super(new MapFullNameStorage());
    }

    @Override
    protected void assertGet(Resume resume){
        Assertions.assertSame(resume, storage.get(resume.getFullName()));
    }

    @Override
    @Test
    void update() {
        storage.update(RESUME_2.getFullName(),RESUME_4);
        assertGet(RESUME_4);
    }

    @Override
    @Test
    void delete() {
        storage.delete(FULL_NAME2);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(FULL_NAME2));
    }
}