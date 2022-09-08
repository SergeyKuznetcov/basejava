package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    public static void main(String[] args) {
        Config config = Config.getInstance();
        Storage storage = new SqlStorage(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
        Resume resume = new Resume("uuid1","name 1");
        storage.save(resume);
        //storage.clear();
    }
}
