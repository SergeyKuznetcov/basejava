package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (directory.canRead() || directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract Resume readFile(File file) throws IOException;

    protected abstract void writeFile(Resume resume, File file) throws IOException;

    private File[] getListFiles(File directory) {
        File[] result = directory.listFiles();
        if (result == null) {
            throw new StorageException("Storage is empty");
        }
        return result;
    }

    @Override
    protected int getSize() {
        return getListFiles(directory).length;
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return readFile(file);
        } catch (IOException e) {
            throw new StorageException("IO error ", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(File file, Resume resume) {
        try {
            writeFile(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error ", file.getName(), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            writeFile(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error ", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getAllResumes() {
        List<Resume> resumes = new ArrayList<>();

        for (File file :
                getListFiles(directory)) {
            resumes.add(getResume(file));
        }
        return resumes;
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException(file.getName() + " was not deleted", file.getName());
        }
        ;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        for (File file :
                getListFiles(directory)) {
            if (!file.delete()) {
                throw new StorageException(file.getName() + " was not deleted", file.getName());
            }
            ;
        }
    }
}
