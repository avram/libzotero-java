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

package com.gimranov.libzotero.xml;

import com.gimranov.libzotero.model.Privilege;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PrivilegeHandler extends XmlObjectHandler<Privilege> {
    private Privilege object;

    @Override
    public Privilege getObject() {
        return object;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        object = new Privilege();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("access".equalsIgnoreCase(qName)) {
            String group = attributes.getValue("group");
            String library = attributes.getValue("library");
            String write = attributes.getValue("write");
            String files = attributes.getValue("files");
            String notes = attributes.getValue("notes");

            if (group != null) {
                object.setGroupAccess(group, "1".equals(write));
            } else {
                object.setLibrary("1".equals(library));
                object.setWrite("1".equals(write));
                object.setFiles("1".equals(files));
                object.setNotes("1".equals(notes));
            }
        }
        super.startElement(uri, localName, qName, attributes);
    }
}
