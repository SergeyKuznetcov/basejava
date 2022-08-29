package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.*;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract int getSize();

    protected abstract Resume getResume(SK searchKey);

    protected abstract void updateResume(SK searchKey, Resume resume);

    protected abstract void saveResume(Resume resume, SK searchKey);

    protected abstract List<Resume> getAllResumes();

    protected abstract void deleteResume(SK searchKey);

    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            LOGGER.warning("UUID " + uuid + " already exists");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOGGER.warning("UUID " + uuid + " doesn't exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public void update(String uuid, Resume r) {
        LOGGER.info("Update " + r.getUuid());
        updateResume(getExistingSearchKey(uuid), r);
    }

    @Override
    public void save(Resume r) {
        LOGGER.info("Save " + r.getUuid());
        saveResume(r, getNotExistingSearchKey(r.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("Get " + uuid);
        return getResume(getExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("Update " + uuid);
        deleteResume(getExistingSearchKey(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted");
        List<Resume> resumes = getAllResumes();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    @Override
    public int size() {
        LOGGER.info("getSize");
        return getSize();
    }
}
