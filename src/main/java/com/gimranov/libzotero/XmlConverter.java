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

import com.gimranov.libzotero.model.Privilege;
import com.gimranov.libzotero.xml.PrivilegeHandler;
import com.gimranov.libzotero.xml.XmlObjectHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.lang.reflect.Type;

public class XmlConverter implements Converter {
    public static final String MIME_TYPE = "application/xml";

    private SAXParserFactory factory = SAXParserFactory.newInstance();

    @Override
    public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
        try {
            SAXParser parser = factory.newSAXParser();

            if (Privilege.class.equals(type)) {
                XmlObjectHandler<Privilege> privilegeHandler = new PrivilegeHandler();
                parser.parse(typedInput.in(), privilegeHandler);
                return privilegeHandler.getObject();
            } else {
                throw new ConversionException("Unsupported type " + type.toString());
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public TypedOutput toBody(Object o) {
        throw new IllegalArgumentException("Not supported!");
    }
}
