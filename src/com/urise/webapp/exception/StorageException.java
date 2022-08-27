package com.urise.webapp.exception;

import java.util.Random;
import java.util.UUID;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message) {
        super(message);
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }
}
