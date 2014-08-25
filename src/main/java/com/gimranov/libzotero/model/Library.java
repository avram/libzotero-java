package com.gimranov.libzotero.model;

import java.util.Map;

public class Library implements Identifiable {
    private LibraryType type;
    private int id;

    private String name;
    private Map<String, Link> links;

    @Override
    public String getIdentifier() {
        return Integer.toString(id, 10);
    }
}
