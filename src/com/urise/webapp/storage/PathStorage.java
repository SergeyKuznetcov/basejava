package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationStrategy serializationStrategy;

    public PathStorage(String dir, SerializationStrategy serializationStrategy) {
        directory = Paths.get(dir);
        this.serializationStrategy = serializationStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or readable/writable");
        }
    }

    @Override
    public void clear() {
        getFileList().forEach(this::deleteResume);
    }

    @Override
    protected int getSize() {
        return (int) getFileList().count();
    }

    @Override
    protected Resume getResume(Path searchKey) {
        try {
            return serializationStrategy.readFile(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException(getFileName(searchKey) + " Path reading error", getFileName(searchKey), e);
        }
    }

    @Override
    protected void updateResume(Path searchKey, Resume resume) {
        try {
            serializationStrategy.writeFile(resume, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException(getFileName(searchKey) + " Path writing error", getFileName(searchKey), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path searchKey) {
        updateResume(searchKey,resume);
    }

    @Override
    protected List<Resume> getAllResumes() {
        List<Resume> resumes = new ArrayList<>();
        getFileList().forEach((r)-> resumes.add(getResume(r)));
        return resumes;
    }

    @Override
    protected void deleteResume(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException(getFileName(searchKey) + " Path deleting error", getFileName(searchKey), e);
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.isRegularFile(searchKey);
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFileList(){
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path getting fileList error", null, e);
        }
    }
}
