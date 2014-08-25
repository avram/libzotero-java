/*
 * Copyright (C) 2014 Avram Lyon (ajlyon@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
