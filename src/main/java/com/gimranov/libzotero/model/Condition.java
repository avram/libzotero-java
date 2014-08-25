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

public class Condition {
    private static final String TAG = Condition.class.getCanonicalName();

    @Expose
    private String condition;
    @Expose
    private String operator;
    @Expose
    private String value;

    public String getCondition() {
        return condition;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}
