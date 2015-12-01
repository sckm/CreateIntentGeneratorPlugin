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

import typegenerators.generators.GenericListGenerator;
import util.PsiUtils;

public class ListGeneratorFactory implements TypeGeneratorFactory {
    private TypeGenerator mGenerator = new GenericListGenerator();

    @Override
    public TypeGenerator getGenerator(PsiType psiType) {
        if (PsiUtils.isOfType(psiType, "java.util.ArrayList")) {

            return mGenerator;
        }

        return null;
    }

    private boolean isParcelableType(PsiType psiType) {
        PsiType[] superTypes = psiType.getSuperTypes();

        for (PsiType superType : superTypes) {
            String canonicalText = superType.getCanonicalText();

            if ("android.os.Parcelable".equals(canonicalText)) {
                return true;
            }
        }

        return false;
    }
}
