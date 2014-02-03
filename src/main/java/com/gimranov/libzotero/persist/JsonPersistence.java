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

import com.gimranov.libzotero.model.ObjectVersions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class JsonPersistence implements IPersistence {

    protected abstract String get(String key, String store);

    protected abstract void put(String key, String value, String store);

    private Gson gson = new GsonBuilder().create();

    @Override
    public <T> void persist(String itemKey, T item) {
        put(itemKey, gson.toJson(item), item.getClass().getSimpleName());
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
                    observer.onNext(gson.fromJson(value, tClass));
                    observer.onCompleted();
                }
                return Subscriptions.empty();
            }
        });
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
