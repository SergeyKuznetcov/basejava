package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

abstract class AbstractStorageTest {
    protected final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid3";
    protected static final String UUID_3 = "uuid5";
    protected static final String UUID_4 = "uuid6";
    protected static final String UUID_NOT_EXIST = "dummy";
    protected static final Resume RESUME_1 = new Resume(UUID_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4);
    protected static final Resume[] RESUMES_LIST = {RESUME_1, RESUME_2, RESUME_3};

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected void assertSize(int size){
        Assertions.assertEquals(size, storage.size());
    }

    protected void assertGet(Resume resume){
        Assertions.assertSame(resume, storage.get(resume.getUuid()));
    }

    @BeforeEach
    void setUp() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void updateNotExisting() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(UUID_NOT_EXIST,new Resume());
        });
    }

    @Test
    void update() {
        storage.update(RESUME_2.getUuid(),RESUME_4);
        assertGet(RESUME_4);
    }

    @Test
    void saveExisting() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(RESUME_2);
        });
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getNotExisting() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_NOT_EXIST);
        });
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_2);
        });
    }

    @Test
    void deleteNotExisting() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_NOT_EXIST);
        });
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        Arrays.sort(resumes);
        Assertions.assertEquals(storage.size(),resumes.length);
        Assertions.assertArrayEquals(RESUMES_LIST,resumes);
        for (Resume r :
                resumes) {
            Assertions.assertNotNull(r);
        }
    }

    @Test
    void size() {
        assertSize(3);
    }
}