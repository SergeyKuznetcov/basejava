package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section{
    private final List<Organization> organizations = new ArrayList<>();

    public void add(String name){
        organizations.add(new Organization(name));
    }

    public void add(String name, String link){
        organizations.add(new Organization(name, link));
    }

    public Organization get(String name){
        for (Organization o :
                organizations) {
            if (o.getName().equals(name)){
                return o;
            }
        }
        return null;
    }

    public List<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    private String organizationsToString(){
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
}
