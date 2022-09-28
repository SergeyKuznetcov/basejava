package com.urise.webapp;

import com.urise.webapp.storage.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    public static void main(String[] args) {
        Storage storage = Config.getInstance().getStorage();
        LocalDate date = LocalDate.of(1998, 6, 23);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

        String s = date.format(dateTimeFormatter);
        date = parseDate(s);
        System.out.println(date.format(dateTimeFormatter));
    }

    private static LocalDate parseDate(String date){
        String[] strings = date.split("/");
        return LocalDate.of(Integer.parseInt(strings[1]), Integer.parseInt(strings[0]), 1);
    }
}
