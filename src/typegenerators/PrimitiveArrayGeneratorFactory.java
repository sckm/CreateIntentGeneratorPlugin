/*
 * Copyright (C) 2015 Seesaa Inc.
 * Copyright (C) 2013 Michał Charmas (http://blog.charmas.pl)
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

public class PrimitiveArrayGeneratorFactory implements TypeGeneratorFactory {
    private final HashMap<String, TypeGenerator> handledTypes;

    public PrimitiveArrayGeneratorFactory() {
        handledTypes = new HashMap<>();
        handledTypes.put("boolean[]", new PrimitiveArrayGenerator("Boolean"));
        handledTypes.put("byte[]", new PrimitiveArrayGenerator("Byte"));
        handledTypes.put("char[]", new PrimitiveArrayGenerator("Char"));
        handledTypes.put("double[]", new PrimitiveArrayGenerator("Double"));
        handledTypes.put("float[]", new PrimitiveArrayGenerator("Float"));
        handledTypes.put("short[]", new PrimitiveArrayGenerator("Short"));
        handledTypes.put("int[]", new PrimitiveArrayGenerator("Int"));
        handledTypes.put("long[]", new PrimitiveArrayGenerator("Long"));
    }

    @Override
    public TypeGenerator getGenerator(PsiType psiType) {
        return handledTypes.get(psiType.getCanonicalText());
    }
}
