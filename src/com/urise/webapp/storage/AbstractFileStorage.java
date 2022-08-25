package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.File;
import java.util.List;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    protected abstract void readFile(File file);
    protected abstract void writeFile(Resume resume, File file);

    @Override
    protected int getSize() {
        return 0;
    }

    @Override
    protected Resume getResume(File searchKey) {
        return null;
    }

    @Override
    protected void updateResume(File searchKey, Resume resume) {

    }

    @Override
    protected void saveResume(Resume resume, File searchKey) {

    }

    @Override
    protected List<Resume> getAllResumes() {
        return null;
    }

    @Override
    protected void deleteResume(File searchKey) {

    }

    @Override
    protected File findSearchKey(String uuid) {
        return null;
    }

    @Override
    protected boolean isExist(File searchKey) {
        return false;
    }

    @Override
    public void clear() {

    }
}
