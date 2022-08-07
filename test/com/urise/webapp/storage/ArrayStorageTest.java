package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArrayStorageTest extends AbstractArrayStorageTest{
    private final ArrayStorage arrayStorage = (ArrayStorage) storage;

    ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test
    void findIndex() {
        Assertions.assertEquals(1,arrayStorage.findIndex(UUID_2));
        Assertions.assertEquals(-1,arrayStorage.findIndex(UUID_NOT_EXIST));
    }

    @Test
    void insertElement() {
        arrayStorage.insertElement(RESUME_4,3);
        arrayStorage.size++;
        assertGet(RESUME_4);
    }

    @Test
    void deleteElement() {
        arrayStorage.deleteElement(1);
        arrayStorage.size--;
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            arrayStorage.get(RESUME_2.getUuid());
        });
    }
}