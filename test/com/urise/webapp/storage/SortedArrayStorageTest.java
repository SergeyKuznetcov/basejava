package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SortedArrayStorageTest extends AbstractArrayStorageTest{
    private SortedArrayStorage sortedArrayStorage = (SortedArrayStorage) storage;

    SortedArrayStorageTest(){
        super(new SortedArrayStorage());
    }

    @Test
    void findIndex() {
        Assertions.assertEquals(1,sortedArrayStorage.findIndex("uuid3"));
        Assertions.assertEquals(-5,sortedArrayStorage.findIndex("uuid7"));
    }

    @Test
    void insertElement() {
        Resume testVal = new Resume("uuid2");
        sortedArrayStorage.insertElement(testVal,1);
        sortedArrayStorage.size++;
        Assertions.assertEquals(testVal,sortedArrayStorage.get(testVal.getUuid()));
    }

    @Test
    void deleteElement() {
        sortedArrayStorage.deleteElement(3);
        sortedArrayStorage.size--;
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            sortedArrayStorage.get("uuid6");
        });
    }
}