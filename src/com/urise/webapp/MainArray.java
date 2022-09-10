package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    public static void main(String[] args) {
        Storage storage = Config.getInstance().getStorage();
        Resume resume = new Resume("a97b3ac3-3817-4c3f-8a5f-178497311f1d","name1");
        storage.clear();
        System.out.println(resume);
        storage.save(resume);
        System.out.println(storage.get("a97b3ac3-3817-4c3f-8a5f-178497311f1d"));

    }
}
