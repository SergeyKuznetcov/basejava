package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private final List<Organization> organizations = new ArrayList<>();

    public OrganizationSection() {
    }

    public Organization get(String name) {
        for (Organization o :
                organizations) {
            if (o.getName().equals(name)) {
                return o;
            }
        }
        return null;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    private String organizationsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organization organization :
                organizations) {
            stringBuilder.append(organization.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return organizationsToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }
}
