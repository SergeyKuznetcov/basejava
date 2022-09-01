package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.XmlStreamSerialization;
import org.junit.jupiter.api.Assertions;

class XmlStreamPathStorageTest extends AbstractStorageTest{

    XmlStreamPathStorageTest() {
        super(new PathStorage("C:\\javaProjects\\basejava\\storage",new XmlStreamSerialization()));
    }

    @Override
    void update() {
        storage.update(RESUME_2.getUuid(), RESUME_4);
        Assertions.assertTrue(RESUME_4.equals(storage.get(RESUME_2.getUuid())));
    }
}