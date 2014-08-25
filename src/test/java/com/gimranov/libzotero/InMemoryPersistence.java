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

package com.gimranov.libzotero;

import com.gimranov.libzotero.persist.JsonPersistence;
import rx.Observable;
import rx.functions.Func1;

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
