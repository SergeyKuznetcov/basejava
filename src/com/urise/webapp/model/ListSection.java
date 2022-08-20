package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section{
    private final List<String> descriptions = new ArrayList<>();

    public void add(String description){
        descriptions.add(description);
    }

    public List<String> getAll() {
        return new ArrayList<>(descriptions);
    }

    private String descriptionsToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String str :
                descriptions) {
            stringBuilder.append(str).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "\n" + descriptionsToString();
    }
}
