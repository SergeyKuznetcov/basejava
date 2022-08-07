package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractArrayStorageTest {
    protected final Storage storage;
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid3";
    static final String UUID_3 = "uuid5";
    static final String UUID_4 = "uuid6";
    static final String UUID_NOT_EXIST = "dummy";
    static final Resume RESUME_1 = new Resume(UUID_1);
    static final Resume RESUME_2 = new Resume(UUID_2);
    static final Resume RESUME_3 = new Resume(UUID_3);
    static final Resume RESUME_4 = new Resume(UUID_4);

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected void assertSize(int size){
        Assertions.assertEquals(size, storage.size());
    }

    protected void assertGet(Resume resume){
        Assertions.assertTrue(resume == storage.get(resume.getUuid()));
    }

    @BeforeEach
    void init() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }


    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
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
        Assertions.assertTrue(RESUME_4 == storage.get(RESUME_4.getUuid()));
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
    void saveOverflow(){
        storage.clear();
        try{
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        }catch (StorageException e){
            Assertions.fail("Early overflow");
        }
        Assertions.assertThrows(StorageException.class, () -> {
            storage.save(new Resume());
        });
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
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_NOT_EXIST);
        });
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        Assertions.assertEquals(storage.size(),resumes.length);
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