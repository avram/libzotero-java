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

import com.gimranov.libzotero.model.Item;
import com.gimranov.libzotero.persist.PersistenceAction;
import com.gimranov.libzotero.rx.FlattenIteratorFunc;
import com.gimranov.libzotero.rx.PersistenceLoadFunc;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.observables.BlockingObservable;

import java.util.List;

import static com.gimranov.libzotero.Credentials.ACCESS_KEY;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class FetchAndStoreTests {
    private ZoteroService zoteroService;
    private InMemoryPersistence persistence;


    @Before
    public void setUp() throws Exception {
        RequestInterceptor authorizingInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION_BEARER_X + ACCESS_KEY);
                request.addHeader(HttpHeaders.ZOTERO_API_VERSION, "3");
            }
        };

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://api.zotero.org")
                .setRequestInterceptor(authorizingInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new Gson()))
                .build();

        zoteroService = adapter.create(ZoteroService.class);

        persistence = new InMemoryPersistence();
    }

    @After
    public void tearDown() throws Exception {
        persistence.clear();
    }

    @Test
    public void testGetItemPersist() throws Exception {
        Observable<List<Item>> itemObservable = zoteroService.getItems(LibraryType.USER, Credentials.USER_ID, null, null);

        Observable<List<Item>> cachingObservable = itemObservable.doOnNext(new PersistenceAction(persistence));

        List<Item> results = BlockingObservable.from(cachingObservable).first();

        assertTrue(!results.isEmpty());
        Item persisted = BlockingObservable.from(persistence.load(results.get(0).getKey(), Item.class)).first();
        assertNotNull(persisted);
        assertEquals(results.get(0).getKey(), persisted.getKey());
    }

    @Test
    public void testUse304() throws Exception {
        PersistenceAction persistenceAction = new PersistenceAction(persistence);
        Observable<List<Item>> itemObservable = zoteroService.getItems(LibraryType.USER, Credentials.USER_ID, null, null)
                .doOnNext(persistenceAction);

        // Make the request and persist it all
        List<Item> results = BlockingObservable.from(itemObservable).first();

        Observable<Item> recoveringObservable = zoteroService.getItems(LibraryType.USER, Credentials.USER_ID, null, "1000")
                .flatMap(new FlattenIteratorFunc<Item>())
                .onErrorResumeNext(new PersistenceLoadFunc<>(Item.class, persistence));

        List<Item> cachedResults = recoveringObservable.toList().toBlocking().first();

        assertFalse(cachedResults.isEmpty());
        assertEquals(results.size(), cachedResults.size());

    }
}
