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

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Group {

    @Expose
    private String name;
    @Expose
    private int owner;
    @Expose
    private String type;
    @Expose
    private String description;
    @Expose
    private String url;
    @Expose
    private String libraryEditing;
    @Expose
    private String libraryReading;
    @Expose
    private String fileEditing;
    @Expose
    private int[] members = {};

    public String getName() {
        return name;
    }

    public int getOwner() {
        return owner;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getLibraryEditing() {
        return libraryEditing;
    }

    public String getLibraryReading() {
        return libraryReading;
    }

    public String getFileEditing() {
        return fileEditing;
    }

    public int[] getMembers() {
        return members;
    }
}
