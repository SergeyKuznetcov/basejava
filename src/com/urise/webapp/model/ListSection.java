package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> descriptions = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(String...descriptions) {
        this.descriptions.addAll(Arrays.asList(descriptions));
    }

    public void add(String description) {
        descriptions.add(description);
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    private String descriptionsToString() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return descriptions.equals(that.descriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptions);
    }
}
