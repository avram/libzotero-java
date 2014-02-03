
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

package com.gimranov.libzotero.model;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String itemType;

    private int itemVersion;

    private String title;
    
    private List<Creator> creators = new ArrayList<>();
    
    private String abstractNote;
    
    private String series;
    
    private String seriesNumber;
    
    private String volume;
    
    private String numberOfVolumes;
    
    private String edition;
    
    private String place;
    
    private String publisher;
    
    private String date;
    
    private String numPages;
    
    private String language;
    
    private String ISBN;
    
    private String shortTitle;
    
    private String url;
    
    private String accessDate;
    
    private String archive;
    
    private String archiveLocation;
    
    private String libraryCatalog;
    
    private String callNumber;
    
    private String rights;
    
    private String extra;
    
    private List<Tag> tags = new ArrayList<Tag>();

    public String getItemType() {
        return itemType;
    }

    public int getItemVersion() {
        return itemVersion;
    }

    public String getTitle() {
        return title;
    }

    public List<Creator> getCreators() {
        return creators;
    }

    public String getAbstractNote() {
        return abstractNote;
    }

    public String getSeries() {
        return series;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public String getVolume() {
        return volume;
    }

    public String getNumberOfVolumes() {
        return numberOfVolumes;
    }

    public String getEdition() {
        return edition;
    }

    public String getPlace() {
        return place;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDate() {
        return date;
    }

    public String getNumPages() {
        return numPages;
    }

    public String getLanguage() {
        return language;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getUrl() {
        return url;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public String getArchive() {
        return archive;
    }

    public String getArchiveLocation() {
        return archiveLocation;
    }

    public String getLibraryCatalog() {
        return libraryCatalog;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public String getRights() {
        return rights;
    }

    public String getExtra() {
        return extra;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
