package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File file = new File("C:\\javaProjects\\basejava\\src");
        printFileList(file);
    }

    public static void printFileList(File directory){
        System.out.println(directory.getName() + " {");
        for (File file :
                directory.listFiles()) {
            if (file.isDirectory()){
                printFileList(file);
            }else {
                System.out.println(file.getName());
            }
        }
        System.out.println(" }");
    }
}
