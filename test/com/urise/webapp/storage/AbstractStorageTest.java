package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

abstract class AbstractStorageTest {
    protected static final Config config = Config.getInstance();
    protected static final File STORAGE_DIR = config.getStorageDir();
    protected final Storage storage;
    protected static final String UUID_1 = "7de882da-02f2-4d16-8daa-60660aaf4071";
    protected static final String UUID_2 = "a97b3ac3-3817-4c3f-8a5f-178497311f1d";
    protected static final String UUID_3 = "dd0a70d1-5ed3-479a-b452-d5e04f21ca73";
    protected static final String UUID_4 = "uuid6";
    protected static final String FULL_NAME1 = "fullName1";
    protected static final String FULL_NAME2 = "fullName3";
    protected static final String FULL_NAME3 = "fullName5";
    protected static final String FULL_NAME4 = "fullName6";
    protected static final String UUID_NOT_EXIST = "dummy";
    protected static final Resume RESUME_1 = ResumeTestData.getResume(UUID_1, FULL_NAME1);
    protected static final Resume RESUME_2 = ResumeTestData.getResume(UUID_2, FULL_NAME2);
    protected static final Resume RESUME_3 = ResumeTestData.getResume(UUID_3, FULL_NAME3);
    protected static final Resume RESUME_4 = ResumeTestData.getResume(UUID_4, FULL_NAME4);
    protected static final Resume[] RESUMES_LIST = {RESUME_1, RESUME_2, RESUME_3};

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    protected void assertGet(Resume resume) {
        Assertions.assertTrue(resume.equals(storage.get(resume.getUuid())));
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void updateNotExisting() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(UUID_NOT_EXIST, new Resume("Dummy")));
    }

    @Test
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
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
        List<Resume> resumes = storage.getAllSorted();
        Assertions.assertEquals(storage.size(), resumes.size());
        Assertions.assertTrue(resumes.containsAll(Arrays.asList(RESUMES_LIST)));
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