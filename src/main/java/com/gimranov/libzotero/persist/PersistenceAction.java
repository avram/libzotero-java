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
