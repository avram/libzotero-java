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

package com.gimranov.libzotero.rx;

import com.gimranov.libzotero.persist.IPersistence;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Func1;

public class PersistenceLoadFunc<T> implements Func1<Throwable, Observable<T>> {

    private Class<T> tClass;
    private IPersistence persistence;

    public PersistenceLoadFunc(Class<T> tClass, IPersistence persistence) {
        this.tClass = tClass;
        this.persistence = persistence;
    }

    @Override
    public Observable<T> call(Throwable throwable) {
        if (throwable instanceof RetrofitError) {
            if (((RetrofitError) throwable).getResponse().getStatus() == 304) {
                return persistence.load(tClass);
            }
        }
        return Observable.error(throwable);
    }
}
