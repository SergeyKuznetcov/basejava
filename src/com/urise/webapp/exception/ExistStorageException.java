package com.urise.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super("UUID " + uuid + "already exists",uuid);
    }
}
