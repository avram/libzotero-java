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

public class Search {
    private static final String TAG = Search.class.getCanonicalName();

    @Expose
    private String searchKey;
    @Expose
    private int searchVersion;
    @Expose
    private String name;
    @Expose
    private List<Condition> conditions = new ArrayList<>();

    public String getSearchKey() {
        return searchKey;
    }

    public int getSearchVersion() {
        return searchVersion;
    }

    public String getName() {
        return name;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
