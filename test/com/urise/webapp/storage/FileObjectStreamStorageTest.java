package com.urise.webapp.storage;

import org.junit.jupiter.api.Assertions;

import java.io.File;

class FileObjectStreamStorageTest extends AbstractStorageTest{

    FileObjectStreamStorageTest() {
        super(new FileStorage(new File("C:\\javaProjects\\basejava\\storage"), new ObjectStreamSerialization()));
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}