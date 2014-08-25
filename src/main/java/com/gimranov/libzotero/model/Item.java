package com.gimranov.libzotero.model;

import java.util.Map;

public class Item implements Versioned, Identifiable {
    private String key;
    private int version;

    private Map<String, Link> links;

    private Meta meta;
    private ItemData data;


    public String getKey() {
        return key;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public Meta getMeta() {
        return meta;
    }

    public ItemData getData() {
        return data;
    }

    @Override
    public String getIdentifier() {
        return key;
    }

    @Override
    public int getVersion() {
        return version;
    }
}
