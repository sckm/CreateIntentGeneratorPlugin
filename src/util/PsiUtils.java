/*
 * Copyright (C) 2015 Michał Charmas (http://blog.charmas.pl)
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

package util;

import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiType;

import java.util.ArrayList;
import java.util.List;

/**
 * Utils for introspecting Psi* stuff
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
final public class PsiUtils {
    private PsiUtils() {
    }

    /**
     * Resolves generics on the given type and returns them (if any) or null if there are none
     *
     * @param type
     * @return
     */
    public static List<PsiType> getResolvedGenerics(PsiType type) {
        List<PsiType> psiTypes = null;

        if (type instanceof PsiClassType) {
            PsiClassType pct = (PsiClassType) type;
            psiTypes = new ArrayList<PsiType>(pct.resolveGenerics().getSubstitutor().getSubstitutionMap().values());
        }

        return psiTypes;
    }

    public static boolean isOfType(PsiType type, String canonicalName) {
        if (type.getCanonicalText().equals(canonicalName)) {
            return true;
        }

        if (getNonGenericType(type).equals(canonicalName)) {
            return true;
        }

        for (PsiType iterType : type.getSuperTypes()) {
            if (isOfType(iterType, canonicalName)) {
                return true;
            }
        }

        return false;
    }

    public static String getNonGenericType(PsiType type) {
        if (type instanceof PsiClassType) {
            PsiClassType pct = (PsiClassType) type;
            return pct.resolve().getQualifiedName();
        }

        return type.getCanonicalText();
    }
}
