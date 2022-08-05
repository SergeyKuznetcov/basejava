package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractArrayStorageTest {
    protected Storage storage;

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void init() {
        storage.clear();
        storage.save(new Resume("uuid1"));
        storage.save(new Resume("uuid3"));
        storage.save(new Resume("uuid5"));
        storage.save(new Resume("uuid6"));
        storage.save(new Resume("uuid8"));
    }


    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void updateNotExistedElem() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update("uuid4",new Resume());
        });
    }

    @Test
    void updateExistedElem() {
        Resume testVal = new Resume("uuid3");
        storage.update("uuid3",testVal);
        Assertions.assertEquals(testVal,storage.get("uuid3"));
    }

    @Test
    void saveExistedElem() {
        ExistStorageException thrown = Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume("uuid6"));
        });
    }

    @Test
    void saveNotExistedElem() {
        Resume testVal = new Resume("uuid10");
        storage.save(testVal);
        Assertions.assertEquals(6,storage.size());
        Assertions.assertEquals(testVal,storage.get(testVal.getUuid()));
    }

    @Test
    void saveOverflow(){
        storage.clear();
        try{
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume());
            }
        }catch (StorageException e){
            Assertions.fail("Early overflow");
        }
        StorageException thrown = Assertions.assertThrows(StorageException.class, () -> {
            storage.save(new Resume());
        });
    }

    @Test
    void getExistedElem() {
        Resume testVal = new Resume();
        storage.save(testVal);
        Assertions.assertEquals(testVal,storage.get(testVal.getUuid()));
    }

    @Test
    void getNotExistedElem() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("uuid2");
        });
    }

    @Test
    void deleteExistedElem() {
        storage.delete("uuid3");
        Assertions.assertEquals(4,storage.size());
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("uuid3");
        });
    }

    @Test
    void deleteNotExistedElem() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete("uuid4");
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
        Assertions.assertEquals(5, storage.size());
    }
}