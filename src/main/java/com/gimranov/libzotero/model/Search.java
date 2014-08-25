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
