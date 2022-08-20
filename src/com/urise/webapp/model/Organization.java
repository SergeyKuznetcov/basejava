package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

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

    public void addPeriod(String dayFrom, String dayTo, String title, String description){
        periods.add(new Period(dayFrom,dayTo,title,description));
    }

    public void addPeriod(String dayFrom, String dayTo, String title){
        periods.add(new Period(dayFrom,dayTo,title));
    }

    public List<Period> getPeriods() {
        return new ArrayList<>(periods);
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
}
