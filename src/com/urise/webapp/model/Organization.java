package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String name;
    private String link;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String name) {
        this.name = name;
        this.link = "";
    }

    public Organization(String name, String link) {
        this.name = name;
        this.link = link;
    }


    public List<Period> getPeriods() {
        return periods;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String periodsToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Period period :
                periods) {
            stringBuilder.append(period.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return name + "\n" + link + "\n"+ periodsToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) && Objects.equals(link, that.link) && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link, periods);
    }
}
