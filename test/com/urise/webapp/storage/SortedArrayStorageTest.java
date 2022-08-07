package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SortedArrayStorageTest extends AbstractArrayStorageTest{
    private final SortedArrayStorage sortedArrayStorage = (SortedArrayStorage) storage;

    SortedArrayStorageTest(){
        super(new SortedArrayStorage());
    }

    @Test
    void findIndex() {
        Assertions.assertEquals(1,sortedArrayStorage.findIndex(UUID_2));
        Assertions.assertEquals(-4,sortedArrayStorage.findIndex(UUID_4));
    }

    @Test
    void insertElement() {

        sortedArrayStorage.insertElement(RESUME_4,3);
        sortedArrayStorage.size++;
        assertGet(RESUME_4);
    }

    @Test
    void deleteElement() {
        sortedArrayStorage.deleteElement(1);
        sortedArrayStorage.size--;
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            sortedArrayStorage.get(UUID_2);
        });
    }
}