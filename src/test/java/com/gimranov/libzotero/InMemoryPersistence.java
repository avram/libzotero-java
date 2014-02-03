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

package com.gimranov.libzotero;

import com.gimranov.libzotero.persist.JsonPersistence;
import rx.Observable;
import rx.util.functions.Func1;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPersistence extends JsonPersistence {
    private Map<String, Map<String, String>> store = new HashMap<>();

    @Override
    protected String get(String key, String store) {
        return getStore(store).get(key);
    }

    @Override
    protected void put(String key, String value, String store) {
        getStore(store).put(key, value);
    }

    @Override
    public <T> Observable<T> load(final Class<T> tClass) {
        return Observable.from(getStore(tClass.getSimpleName()).values()).map(new Func1<String, T>() {
            @Override
            public T call(String s) {
                return deserialize(s, tClass);
            }
        });
    }

    private Map<String, String> getStore(String name) {
        Map<String, String> map = store.get(name);
        if (map == null) {
            map = new HashMap<>();
            store.put(name, map);
        }

        return map;
    }

    public void clear() {
        store.clear();
    }
}
