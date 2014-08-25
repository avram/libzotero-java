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

package com.gimranov.libzotero.persist;

import com.gimranov.libzotero.model.Identifiable;
import rx.functions.Action1;

import java.util.List;

public class PersistenceAction implements Action1<List> {
    private IPersistence persistence;

    public PersistenceAction(IPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void call(List ts) {
        for (Object identifiable : ts) {
            if (identifiable instanceof Identifiable) {
                persistence.persist(((Identifiable) identifiable).getIdentifier(), identifiable);
            }
        }
    }
}
