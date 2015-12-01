/*
 * Copyright (C) 2015 Seesaa Inc.
 * Copyright (C) 2015 Dallas Gutauckis (http://dallasgutauckis.com)
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

import typegenerators.generators.SerializableObjectGenerator;

public class SerializableGeneratorFactory implements TypeGeneratorFactory {
    private TypeGenerator mSerializer;

    public SerializableGeneratorFactory() {
        mSerializer = new SerializableObjectGenerator();
    }

    @Override
    public TypeGenerator getGenerator(PsiType psiType) {
        PsiType[] superTypes = psiType.getSuperTypes();

        for (PsiType superType : superTypes) {
            String canonicalText = superType.getCanonicalText();

            if ("java.io.Serializable".equals(canonicalText)) {
                return mSerializer;
            }
        }

        return null;
    }
}
