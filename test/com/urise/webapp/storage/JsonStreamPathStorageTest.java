package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.JsonStreamSerialization;
import org.junit.jupiter.api.Assertions;

class JsonStreamPathStorageTest extends AbstractStorageTest{

    JsonStreamPathStorageTest() {
        super(new PathStorage("C:\\javaProjects\\basejava\\storage",new JsonStreamSerialization()));
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}