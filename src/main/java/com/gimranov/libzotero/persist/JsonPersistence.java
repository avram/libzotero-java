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

import com.gimranov.libzotero.model.ObjectVersions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import java.util.Map;

public abstract class JsonPersistence implements IPersistence {

    protected abstract String get(String key, String store);

    protected abstract void put(String key, String value, String store);

    private Gson gson = new GsonBuilder().create();

    protected <T> T deserialize(String serialized, Class<T> tClass) {
        return gson.fromJson(serialized, tClass);
    }

    protected <T> String serialize(T item) {
        return gson.toJson(item);
    }


    @Override
    public <T> void persist(String itemKey, T item) {
        put(itemKey, serialize(item), item.getClass().getSimpleName());
    }

    @Override
    public <T> Observable<T> load(final String key, final Class<T> tClass) {
        return Observable.create(new Observable.OnSubscribeFunc<T>() {
            @Override
            public Subscription onSubscribe(Observer<? super T> observer) {
                String value = get(key, tClass.getSimpleName());

                if (value == null) {
                    observer.onError(new ItemNotFoundException());
                } else {
                    observer.onNext(deserialize(value, tClass));
                    observer.onCompleted();
                }
                return Subscriptions.empty();
            }
        });
    }

    @Override
    public <T> void persist(Map<String, T> items) {
        for (Map.Entry<String, T> entry : items.entrySet()) {
            persist(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void persist(ObjectVersions objectVersions) {

    }

    @Override
    public Observable<Integer> loadItemVersion(String key) {
        return null;
    }

    @Override
    public void persistLibraryVersion(int libraryVersion) {

    }

    @Override
    public Observable<Integer> loadLibraryVersion() {
        return null;
    }
}
