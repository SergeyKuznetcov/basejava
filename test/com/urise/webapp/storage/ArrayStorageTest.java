package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArrayStorageTest extends AbstractArrayStorageTest{
    private ArrayStorage arrayStorage = (ArrayStorage) storage;

    ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test
    void findIndex() {
        Assertions.assertEquals(3,arrayStorage.findIndex("uuid6"));
        Assertions.assertEquals(-1,arrayStorage.findIndex("uuid10"));
    }

    @Test
    void insertElement() {
        Resume testVal = new Resume("uuid2");
        arrayStorage.insertElement(testVal,5);
        arrayStorage.size++;
        Assertions.assertEquals(testVal,arrayStorage.get(testVal.getUuid()));
    }

    @Test
    void deleteElement() {
        arrayStorage.deleteElement(3);
        arrayStorage.size--;
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            arrayStorage.get("uuid6");
        });
        Assertions.assertEquals(-1,arrayStorage.findIndex("uuid6"));
    }
}