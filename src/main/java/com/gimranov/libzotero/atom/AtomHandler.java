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

package com.gimranov.libzotero.atom;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a very minimal parser for the Atom format-- it ignores all but the &lt;content> nodes on &lt;entry> nodes of the
 * Atom document.
 */
public class AtomHandler extends DefaultHandler {
    private static final String ENTRY_NODE = "entry";
    private static final String FEED_NODE = "feed";
    private static final String CONTENT_NODE = "content";

    private Entry currentEntry;

    private List<Entry> entries;

    private StringBuilder stringBuilder;

    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        entries = new ArrayList<>();
        stringBuilder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (ENTRY_NODE.equalsIgnoreCase(qName)) {
            currentEntry = new Entry();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (currentEntry != null) {
            if (CONTENT_NODE.equalsIgnoreCase(qName)) {
                currentEntry.getContentEntries().add(stringBuilder.toString());
            } else if (ENTRY_NODE.equalsIgnoreCase(qName)) {
                entries.add(currentEntry);
            }

            stringBuilder.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        stringBuilder.append(ch, start, length);
    }
}
