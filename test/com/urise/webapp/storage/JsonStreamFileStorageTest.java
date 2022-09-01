package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.JsonStreamSerialization;
import org.junit.jupiter.api.Assertions;

import java.io.File;

class JsonStreamFileStorageTest extends AbstractStorageTest{

    JsonStreamFileStorageTest() {
        super(new FileStorage(new File("C:\\javaProjects\\basejava\\storage"),new JsonStreamSerialization()));
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}