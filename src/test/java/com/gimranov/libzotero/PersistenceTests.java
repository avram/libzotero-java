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
