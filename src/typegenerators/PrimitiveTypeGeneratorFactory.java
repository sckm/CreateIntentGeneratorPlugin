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
import java.util.Map;

import typegenerators.generators.PrimitiveTypeGenerator;

public class PrimitiveTypeGeneratorFactory implements TypeGeneratorFactory {

    private final Map<String, TypeGenerator> writeMethodsForTypes = new HashMap<String, TypeGenerator>();

    public PrimitiveTypeGeneratorFactory() {
        initPrimitives();
        initNullablePrimitives();
    }

    private void initNullablePrimitives() {
        writeMethodsForTypes.put("byte", new PrimitiveTypeGenerator("Byte", "(byte) -1"));
        writeMethodsForTypes.put("double", new PrimitiveTypeGenerator("Double", "-1.0"));
        writeMethodsForTypes.put("float", new PrimitiveTypeGenerator("Float", "-1.0f"));
        writeMethodsForTypes.put("short", new PrimitiveTypeGenerator("Short", "(short) -1.0"));
        writeMethodsForTypes.put("int", new PrimitiveTypeGenerator("Int", "-1"));
        writeMethodsForTypes.put("long", new PrimitiveTypeGenerator("Long", "-1L"));
        writeMethodsForTypes.put("boolean", new PrimitiveTypeGenerator("Boolean", "false"));
        writeMethodsForTypes.put("char", new PrimitiveTypeGenerator("Char", "Character.MIN_VALUE"));
    }

    private void initPrimitives() {
//        writeMethodsForTypes.put("java.lang.Byte", new NullablePrimitivesGenerator("java.lang.Byte"));
//        writeMethodsForTypes.put("java.lang.Double", new NullablePrimitivesGenerator("java.lang.Double"));
//        writeMethodsForTypes.put("java.lang.Float", new NullablePrimitivesGenerator("java.lang.Float"));
//        writeMethodsForTypes.put("java.lang.Integer", new NullablePrimitivesGenerator("java.lang.Integer"));
//        writeMethodsForTypes.put("java.lang.Long", new NullablePrimitivesGenerator("java.lang.Long"));
//        writeMethodsForTypes.put("java.lang.Boolean", new NullablePrimitivesGenerator("java.lang.Boolean"));
//        writeMethodsForTypes.put("java.lang.Char", new NullablePrimitivesGenerator("java.lang.Char"));
    }

    @Override
    public TypeGenerator getGenerator(PsiType psiType) {
        return writeMethodsForTypes.get(psiType.getCanonicalText());
    }
}
