package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    Resume readFile(InputStream inputStream) throws IOException;

    void writeFile(Resume resume, OutputStream outputStream) throws IOException;
}
