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

import com.gimranov.libzotero.model.Collection;
import com.gimranov.libzotero.model.Group;
import com.gimranov.libzotero.model.Item;
import com.gimranov.libzotero.model.Key;
import com.gimranov.libzotero.model.ObjectVersions;
import com.gimranov.libzotero.model.Search;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.BlockingObservable;

import java.util.ArrayList;
import java.util.List;

import static com.gimranov.libzotero.Credentials.ACCESS_KEY;
import static com.gimranov.libzotero.Credentials.USER_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ServiceTests {

    ZoteroService zoteroService;

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
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(authorizingInterceptor)
                .setConverter(new GsonConverter(new Gson()))
                .build();

        zoteroService = adapter.create(ZoteroService.class);
    }

    @Test
    public void testItemListLoad() throws Exception {
        rx.Observable<List<Item>> itemObservable = zoteroService.getItems(LibraryType.USER, USER_ID, null, null);
        assertTrue(BlockingObservable.from(itemObservable).first().size() > 0);
    }

    @Test
    public void testItemList304() throws Exception {
        rx.Observable<List<Item>> itemObservable = zoteroService.getItems(LibraryType.USER, USER_ID, null, "10000");
        assertTrue(BlockingObservable.from(itemObservable.onErrorReturn(new Func1<Throwable, List<Item>>() {
            @Override
            public List<Item> call(Throwable throwable) {
                if (throwable instanceof RetrofitError) {
                    if (((RetrofitError) throwable).getResponse().getStatus() == 304) {
                        List<Item> existing = new ArrayList<>();
                        existing.add(new Item());
                        return existing;
                    }
                }

                return null;
            }
        })).first().size() == 1);
    }

    @Test
    public void testItemListWithFlatMap() throws Exception {
        Item item = zoteroService.getItems(LibraryType.USER, USER_ID, null, null)
                .flatMap(new Func1<List<Item>, Observable<Item>>() {
                    @Override
                    public Observable<Item> call(List<Item> response) {
                        return Observable.from(response);
                    }
                })
                .toBlocking().first();

        assertNotNull(item);
    }

    @Test
    public void testItemVersions() throws Exception {
        rx.Observable<ObjectVersions> versionsObservable = zoteroService.getItemVersions(LibraryType.USER, USER_ID, null, null);
        ObjectVersions versions = BlockingObservable.from(versionsObservable).first();

        Integer version = versions.getVersionForItem("EVZNKA2M");
        assertNotNull(version);
        assertEquals(1, version.intValue());
    }

    @Test
    public void testCollectionLoad() throws Exception {
        rx.Observable<List<Collection>> collections = zoteroService.getCollections(LibraryType.USER, USER_ID, null);
        assertTrue(BlockingObservable.from(collections).first().size() > 0);
    }

    @Test
    public void testSearchList() throws Exception {
        rx.Observable<List<Search>> searches = zoteroService.getSearches(LibraryType.USER, USER_ID, null);
        List<Search> searchList = BlockingObservable.from(searches).first();

        // We have no searches on the test account right now
        assertTrue(searchList.isEmpty());
    }

    @Test
    public void testSingleItem() throws Exception {
        rx.Observable<Item> itemObservable = zoteroService.getItem(LibraryType.USER, USER_ID, "EVZNKA2M", null);
        Item bill = BlockingObservable.from(itemObservable).first();
        assertEquals(1, bill.getVersion());
        assertEquals("journalArticle", bill.getData().getItemType());
    }

    @Test
    public void testSingleItem304() throws Exception {
        rx.Observable<Item> itemObservable = zoteroService.getItem(LibraryType.USER, USER_ID, "EVZNKA2M", "1");
        Item bill = BlockingObservable.from(itemObservable).first();
        assertEquals(1, bill.getVersion());
        assertEquals("journalArticle", bill.getData().getItemType());
    }

    @Test
    public void testGroupList() throws Exception {
        rx.Observable<List<Group>> groupObservable = zoteroService.getGroups(USER_ID);

        List<Group> groups = BlockingObservable.from(groupObservable).first();
        assertTrue(!groups.isEmpty());
    }

    @Test
    public void testKeyPrivileges() throws Exception {
        rx.Observable<Key> privilegeObservable = zoteroService.getPrivileges(USER_ID, ACCESS_KEY);

        Key key = BlockingObservable.from(privilegeObservable).first();

        assertTrue(key.getAccess().getUser().hasLibrary());

    }

    @Test
    public void testResponseHeader() throws Exception {
        assertEquals(new Integer(1), zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID, null, null)
                .flatMap(new Func1<Response, Observable<Header>>() {
                    @Override
                    public Observable<Header> call(Response response) {
                        return Observable.from(response.getHeaders());
                    }
                })
                .filter(new Func1<Header, Boolean>() {
                    @Override
                    public Boolean call(Header header) {
                        return HttpHeaders.LAST_MODIFIED_VERSION.equalsIgnoreCase(header.getName());
                    }
                })
                .map(new Func1<Header, Integer>() {
                    @Override
                    public Integer call(Header header) {
                        return Integer.valueOf(header.getValue());
                    }
                })
                .toBlocking()
                .first());
    }
}
