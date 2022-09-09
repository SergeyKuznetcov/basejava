package com.urise.webapp.storage;

import org.junit.jupiter.api.Assertions;

class SqlStorageTest extends AbstractStorageTest{

    SqlStorageTest() {
        super(config.getStorage());
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}