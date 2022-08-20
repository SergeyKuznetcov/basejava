package com.urise.webapp.model;

public class Period {
    private String dateFrom;
    private String dateTo;
    private String title;
    private String description;

    public Period(String dateFrom, String dateTo, String title) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.description = "";
    }

    public Period(String dateFrom, String dateTo, String title, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.description = description;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return dateFrom + " - " + dateTo + "\n" + title + "\n" + description + "\n";
    }
}
