package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import org.junit.jupiter.api.Assertions;

class SqlStorageTest extends AbstractStorageTest{

    SqlStorageTest() {
        super(new SqlStorage(config.getDbUrl(), config.getDbUser(), config.getDbPassword()));
    }

    @Override
    void saveExisting() {
        Assertions.assertThrows(StorageException.class, () -> storage.save(RESUME_2));
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}