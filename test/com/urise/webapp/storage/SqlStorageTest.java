package com.urise.webapp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SqlStorageTest extends AbstractStorageTest{

    SqlStorageTest() {
        super(config.getStorage());
    }

    @Override
    @Test
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        RESUME_4.setUuid(RESUME_2.getUuid());
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}