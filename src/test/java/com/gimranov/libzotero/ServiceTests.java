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

import com.gimranov.libzotero.model.*;
import org.junit.Before;
import org.junit.Test;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import rx.observables.BlockingObservable;
import rx.util.functions.Func1;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceTests {

    ZoteroService zoteroService;

    // Credentials of the Zandy user.
    private static final int USER_ID = 743083;
    private static final String ACCESS_KEY = "vLLtTQU1tcWCPb3Grsl6GzWi";

    @Before
    public void setUp() throws Exception {
        RequestInterceptor authorizingInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addEncodedQueryParam("key", ACCESS_KEY);
                request.addHeader("Zotero-API-Version", "2");
            }
        };

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://api.zotero.org")
                .setRequestInterceptor(authorizingInterceptor)
                .setConverter(new ZoteroConverter())
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
        })).first().size() > 0);
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
        assertEquals(1, bill.getItemVersion());
        assertEquals("journalArticle", bill.getItemType());
    }

    @Test
    public void testSingleItem304() throws Exception {
        rx.Observable<Item> itemObservable = zoteroService.getItem(LibraryType.USER, USER_ID, "EVZNKA2M", "1");
        Item bill = BlockingObservable.from(itemObservable).first();
        assertEquals(1, bill.getItemVersion());
        assertEquals("journalArticle", bill.getItemType());
    }

    @Test
    public void testGroupList() throws Exception {
        rx.Observable<List<Group>> groupObservable = zoteroService.getGroups(USER_ID);

        List<Group> groups = BlockingObservable.from(groupObservable).first();
        assertTrue(!groups.isEmpty());
    }

    @Test
    public void testPrivileges() throws Exception {
        rx.Observable<Privilege> privilegeObservable = zoteroService.getPrivileges(USER_ID, ACCESS_KEY);

        Privilege privileges = BlockingObservable.from(privilegeObservable).first();

        assertTrue(privileges.hasLibrary());

    }
}
