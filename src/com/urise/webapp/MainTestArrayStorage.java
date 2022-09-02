package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.*;
import com.urise.webapp.storage.strategy.DataStreamSerialization;
import com.urise.webapp.storage.strategy.SerializationStrategy;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    static final AbstractArrayStorage sortedArrayStorage = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume resume = ResumeTestData.getResume("uuid", "fullName");
        SerializationStrategy serializationStrategy = new DataStreamSerialization();
        Path path = Paths.get("C:\\JavaProjects\\basejava\\storage");
        Storage storage = new PathStorage(path.toString(), serializationStrategy);
        storage.clear();
        storage.save(resume);

        System.out.println(storage.get("uuid"));
        //System.out.println(resume);


        //Method method = resume.getClass().getDeclaredMethod("toString");
        //System.out.println(method.invoke(resume));

    }
}
