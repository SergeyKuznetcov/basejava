package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractArrayStorage;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.SortedArrayStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private static AbstractArrayStorage ARRAY_STORAGE;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            String[] params;
            if (ARRAY_STORAGE == null) {
                System.out.println("Введите одну из команд - (sorted | unsorted | exit): ");
                params = reader.readLine().trim().toLowerCase().split(" ");
                if (params.length < 1 || params.length > 2) {
                    System.out.println("Неверная команда.");
                    continue;
                }
                String uuid = null;
                if (params.length == 2) {
                    uuid = params[1].intern();
                }
                switch (params[0]) {
                    case "sorted":
                        ARRAY_STORAGE = new SortedArrayStorage();
                        break;
                    case "unsorted":
                        ARRAY_STORAGE = new ArrayStorage();
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Неверная команда.");
                        break;
                }
            } else {
                System.out.print("Введите одну из команд - (list | size | save uuid | delete uuid | update oldUuid newUuid | get uuid | exit): ");
                params = reader.readLine().trim().toLowerCase().split(" ");
                if (params.length < 1 || params.length > 2) {
                    System.out.println("Неверная команда.");
                    continue;
                }
                String uuid = null;
                if (params.length == 2) {
                    uuid = params[1].intern();
                }
                switch (params[0]) {
                    case "update":
                        System.out.println("Введите страрое и новое значение.");
                        params = reader.readLine().trim().toLowerCase().split(" ");
                        ARRAY_STORAGE.update(params[0], new Resume(params[1]));
                        printAll();
                        break;
                    case "list":
                        printAll();
                        break;
                    case "size":
                        System.out.println(ARRAY_STORAGE.size());
                        break;
                    case "save":
                        r = new Resume(uuid);
                        ARRAY_STORAGE.save(r);
                        printAll();
                        break;
                    case "delete":
                        ARRAY_STORAGE.delete(uuid);
                        printAll();
                        break;
                    case "get":
                        System.out.println(ARRAY_STORAGE.get(uuid));
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Неверная команда.");
                        break;
                }
            }
        }
    }

    static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
