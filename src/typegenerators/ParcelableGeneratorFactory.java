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

package typegenerators;

import com.intellij.psi.PsiType;

import java.util.List;

import typegenerators.generators.ParcelableArrayGenerator;
import typegenerators.generators.ParcelableListGenerator;
import typegenerators.generators.ParcelableObjectGenerator;
import util.PsiUtils;

/**
 * Serializer factory for Parcelable objects
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 * @author Michał Charmas [micha@charmas.pl]
 */
public class ParcelableGeneratorFactory implements TypeGeneratorFactory {
    private TypeGenerator mGenerator = new ParcelableObjectGenerator();
    private TypeGenerator mListGenerator = new ParcelableListGenerator();
    private TypeGenerator mArrayGenerator = new ParcelableArrayGenerator();

    @Override
    public TypeGenerator getGenerator(PsiType psiType) {
        if (PsiUtils.isOfType(psiType, "android.os.Parcelable[]")) {
            return mArrayGenerator;
        }

        if (PsiUtils.isOfType(psiType, "android.os.Parcelable")) {
            return mGenerator;
        }

        if (PsiUtils.isOfType(psiType, "java.util.List")) {
            List<PsiType> resolvedGenerics = PsiUtils.getResolvedGenerics(psiType);
            for (PsiType resolvedGeneric : resolvedGenerics) {
                if (PsiUtils.isOfType(resolvedGeneric, "android.os.Parcelable")) {
                    return mListGenerator;
                }
            }
        }

        return null;
    }

}
