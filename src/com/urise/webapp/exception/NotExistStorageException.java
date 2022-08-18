package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("UUID " + uuid + " doesn't exist", uuid);
    }
}
