package com.urise.webapp;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    protected static final String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();

    private final Properties props = new Properties();
    private final File storageDir;
    private final Storage storage;

    public Storage getStorage() {
        return storage;
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    private Config() {
        try (InputStream inputStream = Config.class.getResourceAsStream(PROPS)) {
            props.load(inputStream);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config " + PROPS);
        }
    }
}
