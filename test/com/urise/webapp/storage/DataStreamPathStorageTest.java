package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.DataStreamSerialization;
import org.junit.jupiter.api.Assertions;

public class DataStreamPathStorageTest extends AbstractStorageTest{

    DataStreamPathStorageTest() {
        super(new PathStorage("C:\\JavaProjects\\basejava\\storage", new DataStreamSerialization()));
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}
