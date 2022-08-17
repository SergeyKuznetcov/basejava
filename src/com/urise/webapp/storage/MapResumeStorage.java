package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage.replace(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }
}
