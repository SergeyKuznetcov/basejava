package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStreamSerialization;
import org.junit.jupiter.api.Assertions;

class ObjectStreamFileStorageTest extends AbstractStorageTest{

    ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerialization()));
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}