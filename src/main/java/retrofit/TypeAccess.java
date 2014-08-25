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

package retrofit;

import java.lang.reflect.Type;

/**
 * Small hack to reuse Retrofit's type matching logic in our custom ZoteroConverter
 */
public class TypeAccess {
    public static boolean equals(Type a, Type b) {
        return Types.equals(a, b);
    }
}
