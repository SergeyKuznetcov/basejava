package com.urise.webapp;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFile {
    public static void main(String[] args) {
        File file = new File("C:\\javaProjects\\basejava\\src");
        Path path = Paths.get(file.getName());
        printFileList(file, "");
    }


    //HW8 task
    public static void printFileList(File directory, String indent) {
        System.out.println(indent + directory.getName() + " ::");
        for (File file :
                directory.listFiles()) {
            if (file.isDirectory()) {
                printFileList(file, indent + "  ");
            } else {
                System.out.println(indent + "   " + file.getName());
            }
        }
    }
}
