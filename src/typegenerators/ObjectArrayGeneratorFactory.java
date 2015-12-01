/*
 * Copyright (C) 2015 Seesaa Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package typegenerators;

import com.intellij.psi.PsiType;

import java.util.HashMap;

import typegenerators.generators.PrimitiveArrayGenerator;

public class ObjectArrayGeneratorFactory implements TypeGeneratorFactory {
    private final HashMap<String, TypeGenerator> handledTypes;

    public ObjectArrayGeneratorFactory() {
        handledTypes = new HashMap<>();
        handledTypes.put("java.lang.CharSequence[]", new PrimitiveArrayGenerator("CharSequence"));
        handledTypes.put("java.lang.String[]", new PrimitiveArrayGenerator("String"));
    }

    @Override
    public TypeGenerator getGenerator(PsiType psiType) {
        return handledTypes.get(psiType.getCanonicalText());
    }
}
