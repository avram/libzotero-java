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
import org.jetbrains.annotations.Nullable;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

import java.util.List;
import java.util.Map;

public interface ZoteroService {

    /**
     * The set of all items in the library
     *
     * @param searchQuery Search parameters for the result set. May be null to apply no search parameters
     */
    @GET("/{type}/{id}/items?content=json")
    Observable<List<Item>> getItems(@Path("type") LibraryType type,
                                    @Path("id") long id,
                                    @Nullable @QueryMap SearchQuery searchQuery,
                                    @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    @GET("/{type}/{id}/items?format=versions")
    Observable<ObjectVersions> getItemVersions(@Path("type") LibraryType type,
                                               @Path("id") long id,
                                               @Nullable @QueryMap Map searchQuery,
                                               @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    /**
     * The set of all top-level items in the library
     */
    @GET("/{type}/{id}/items/top?content=json")
    Observable<List<Item>> getTopLevelItems(@Path("type") LibraryType type,
                                            @Path("id") long id,
                                            @Nullable @QueryMap Map searchQuery,
                                            @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    /**
     * The set of items in the trash
     */
    @GET("/{type}/{id}/items/trash?content=json")
    Observable<List<Item>> getTrashItems(@Path("type") LibraryType type,
                                         @Path("id") long id,
                                         @Nullable @QueryMap Map searchQuery);

    /**
     * A specific item in the library
     */
    @GET("/{type}/{id}/items/{itemKey}?content=json")
    Observable<Item> getItem(@Path("type") LibraryType type,
                             @Path("id") long id,
                             @Path("itemKey") String itemKey,
                             @Nullable @Header("If-Modified-Since-Version") String itemVersion);

    /**
     * The set of all child items under a specific item
     */
    @GET("/{type}/{id}/items/{itemKey}/children?content=json")
    Observable<List<Item>> getItemChildren(@Path("type") LibraryType type,
                                           @Path("id") long id,
                                           @Path("itemKey") String itemKey);

    /**
     * The set of all tags associated with a specific item
     */
    @GET("/{type}/{id}/items/{itemKey}/tags?content=json")
    Observable<Item> getItemTags(@Path("type") LibraryType type,
                                 @Path("id") long id,
                                 @Path("itemKey") String itemKey);

    /**
     * The set of all tags in the library
     */
    @GET("/{type}/{id}/tags?content=json")
    Observable<List<Item>> getTags(@Path("type") LibraryType type,
                                   @Path("id") long id,
                                   @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    /**
     * The set of tags (i.e., of all types) matching a specific name
     */
    @GET("/{type}/{id}/tags/{tagQuery}?content=json")
    Observable<List<Item>> getTagsMatches(@Path("type") LibraryType type,
                                          @Path("id") long id,
                                          @Path("tagQuery") String urlEncodedTagQuery);

    /**
     * The set of collections in the library
     */
    @GET("/{type}/{id}/collections?content=json")
    Observable<List<Collection>> getCollections(@Path("type") LibraryType type,
                                                @Path("id") long id,
                                                @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    /**
     * The set of all top-level collections in the library
     */
    @GET("/{type}/{id}/collections/top?content=json")
    Observable<List<Collection>> getTopLevelCollections(@Path("type") LibraryType type,
                                                        @Path("id") long id,
                                                        @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    /**
     * A specific collection in the library
     */
    @GET("/{type}/{id}/collections/{collectionKey}?content=json")
    Observable<Collection> getCollection(@Path("type") LibraryType type,
                                         @Path("id") long id,
                                         @Path("collectionKey") String collectionKey);

    /**
     * The set of subcollections within a specific collection in the library
     */
    @GET("/{type}/{id}/collections/{collectionKey}/collections?content=json")
    Observable<List<Collection>> getCollectionSubcollections(@Path("type") LibraryType type,
                                                             @Path("id") long id,
                                                             @Path("collectionKey") String collectionKey);

    /**
     * The set of all items within a specific collection in the library
     */
    @GET("/{type}/{id}/collections/{collectionKey}/items?content=json")
    Observable<List<Item>> getCollectionItems(@Path("type") LibraryType type,
                                              @Path("id") long id,
                                              @Path("collectionKey") String collectionKey,
                                              @Nullable @QueryMap Map searchQuery,
                                              @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    /**
     * The set of top-level items within a specific collection in the library
     */
    @GET("/{type}/{id}/collections/{collectionKey}/items/top?content=json")
    Observable<List<Item>> getCollectionTopLevelitems(@Path("type") LibraryType type,
                                                      @Path("id") long id,
                                                      @Path("collectionKey") String collectionKey,
                                                      @Nullable @QueryMap Map searchQuery,
                                                      @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    /**
     * The set of tags within a specific collection in the library
     */
    @GET("/{type}/{id}/collections/{collectionKey}/tags?content=json")
    Observable<List<Tag>> getCollectionTags(@Path("type") LibraryType type,
                                            @Path("id") long id,
                                            @Path("collectionKey") String collectionKey);

    /**
     * The set of all saved searches in the library
     */
    @GET("/{type}/{id}/searches?content=json")
    Observable<List<Search>> getSearches(@Path("type") LibraryType type,
                                         @Path("id") long id,
                                         @Nullable @Header("If-Modified-Since-Version") String libraryVersion);

    /**
     * A specific saved search in the library
     */
    @GET("/{type}/{id}/searches/{searchKey}?content=json")
    Observable<Search> getSearch(@Path("type") LibraryType type,
                                 @Path("id") long id,
                                 @Path("searchKey") String searchKey);

    /**
     * The set of groups the current API key has access to, including public groups the key owner belongs to even if the
     * key doesn't have explicit permissions for it.
     */
    @GET("/users/{id}/groups?content=json")
    Observable<List<Group>> getGroups(@Path("id") long id);

    /**
     * The privileges of the given API key.
     */
    @GET("/users/{id}/keys/{key}?content=json")
    Observable<Privilege> getPrivileges(@Path("id") long id,
                                        @Path("key") String key);
}
