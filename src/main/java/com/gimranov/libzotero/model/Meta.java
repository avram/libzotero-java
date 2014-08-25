package com.gimranov.libzotero.model;

public class Meta {
    private String creatorSummary;
    private int numChildren;
    private String parsedDate;

    public String getParsedDate() {
        return parsedDate;
    }

    public String getCreatorSummary() {
        return creatorSummary;
    }

    public int getNumChildren() {
        return numChildren;
    }
}
