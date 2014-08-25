package com.gimranov.libzotero.model;

import java.util.Map;

public class Key {
    private String key;

    private KeyAccess access;

    public String getKey() {
        return key;
    }

    public KeyAccess getAccess() {
        return access;
    }

    public static class KeyAccess {
        private Privilege user;
        private Map<String, Privilege> groups;

        public Privilege getUser() {
            return user;
        }

        public Map<String, Privilege> getGroups() {
            return groups;
        }
    }
}
