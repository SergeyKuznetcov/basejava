package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.JsonStreamSerialization;
import org.junit.jupiter.api.Assertions;

class JsonStreamFileStorageTest extends AbstractStorageTest{

    JsonStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR,new JsonStreamSerialization()));
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}