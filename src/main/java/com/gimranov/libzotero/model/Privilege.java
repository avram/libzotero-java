/*
 * Copyright (c) Avram Lyon, 2014.
 *
 * This file is part of libzotero-java.
 *
 * libzotero-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * libzotero-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with libzotero-java.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gimranov.libzotero.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Part of the Scopely™ Platform
 * © 2013 Scopely, Inc.
 */
public class Privilege {

    private boolean library;
    private boolean files;
    private boolean notes;
    private boolean write;

    private Map<String, Boolean> groupAccess = new HashMap<>();

    public boolean hasLibrary() {
        return library;
    }

    public void setLibrary(boolean library) {
        this.library = library;
    }

    public boolean isFiles() {
        return files;
    }

    public void setFiles(boolean files) {
        this.files = files;
    }

    public boolean isNotes() {
        return notes;
    }

    public void setNotes(boolean notes) {
        this.notes = notes;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public void setGroupAccess(String groupKey, boolean hasWriteAccess) {
        groupAccess.put(groupKey, hasWriteAccess);
    }

    public boolean hasGroupAccess(String groupKey) {
        Boolean access = groupAccess.get(groupKey);
        return access == null ? false : access;
    }
}
