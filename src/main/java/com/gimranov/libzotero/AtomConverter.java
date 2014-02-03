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

import com.gimranov.libzotero.atom.AtomHandler;
import com.gimranov.libzotero.atom.Entry;
import com.gimranov.libzotero.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.xml.sax.SAXException;
import retrofit.TypeAccess;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtomConverter implements Converter {
    public static final String MIME_TYPE = "application/atom+xml";

    private static final Map<Class, Type> CONTENT_TYPES = new HashMap<>();

    static {
        CONTENT_TYPES.put(Item.class, new TypeToken<List<Item>>() {
        }.getType());
        CONTENT_TYPES.put(Search.class, new TypeToken<List<Search>>() {
        }.getType());
        CONTENT_TYPES.put(Collection.class, new TypeToken<List<Collection>>() {
        }.getType());
        CONTENT_TYPES.put(Tag.class, new TypeToken<List<Tag>>() {
        }.getType());
        CONTENT_TYPES.put(Group.class, new TypeToken<List<Group>>() {
        }.getType());
    }

    private Gson gson;
    private SAXParserFactory factory = SAXParserFactory.newInstance();

    public AtomConverter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        if (!MIME_TYPE.equalsIgnoreCase(body.mimeType())) {
            throw new ConversionException("MIME type mismatch; unexpected type " + body.mimeType());
        }

        try {
            SAXParser parser = factory.newSAXParser();

            AtomHandler handler = new AtomHandler();

            parser.parse(body.in(), handler);

            List<Entry> entries = handler.getEntries();

            for (Map.Entry<Class, Type> entry : CONTENT_TYPES.entrySet()) {
                if (TypeAccess.equals(type, entry.getValue())) {
                    return parseList(entries, entry.getKey());
                }

                if (TypeAccess.equals(type, entry.getKey())) {
                    return parseEntry(entries.get(0), entry.getKey());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConversionException(e);
        }

        throw new ConversionException("Unsupported type " + type.toString());
    }

    private <T> T parseEntry(Entry entry, Class<T> entryType) {
        return gson.fromJson(entry.getContentEntries().get(0), entryType);
    }

    private <T> List<T> parseList(List<Entry> entries, Class<T> entryType) {
        List<T> items = new ArrayList<>(entries.size());

        for (Entry entry : entries) {
            items.add(parseEntry(entry, entryType));
        }

        return items;
    }

    @Override
    public TypedOutput toBody(Object object) {
        throw new IllegalArgumentException("Not supported!");
    }
}
