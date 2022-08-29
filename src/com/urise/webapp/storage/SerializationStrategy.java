package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    public Resume readFile(InputStream inputStream) throws IOException;
    public void writeFile(Resume resume, OutputStream outputStream) throws IOException;
}
