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

import com.gimranov.libzotero.model.ItemData;
import com.gimranov.libzotero.persist.IPersistence;
import com.gimranov.libzotero.persist.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import rx.observables.BlockingObservable;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertNotNull;

public class PersistenceTests {
    private IPersistence persistence;

    @Before
    public void setUp() throws Exception {
        persistence = new InMemoryPersistence();
    }

    @Test
    public void testWriteAndRead() throws Exception {
        persistence.persist("ABC", new ItemData());

        assertNotNull(BlockingObservable.from(persistence.load("ABC", ItemData.class)).first());
    }

    @Test
    public void testNonexistentItem() throws Exception {
        try {
            BlockingObservable.from(persistence.load("A", ItemData.class)).first();
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof ItemNotFoundException);
            return;
        }

        fail("Should have thrown exception");
    }
}
