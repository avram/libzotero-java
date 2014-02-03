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

import com.google.gson.Gson;
import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.lang.reflect.Type;

public class JsonConverter extends GsonConverter {
    public static final String MIME_TYPE = "application/json";

    public JsonConverter(Gson gson) {
        super(gson);
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        Object object = super.fromBody(body, type);
        return object;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return super.toBody(object);
    }
}
