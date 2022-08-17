package com.urise.webapp.storage;

import org.junit.jupiter.api.Assertions;

class MapResumeStorageTest extends AbstractMapStorageTest{
    MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertSame(RESUME_4, storage.get(RESUME_2.getUuid()));
    }
}