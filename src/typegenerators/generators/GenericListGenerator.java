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

package typegenerators.generators;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;

import org.jetbrains.annotations.NotNull;

public class GenericListGenerator extends AbstractTypeGenerator {
    @Override
    public String putValue(PsiField field, String intentName, String dataName, String variableName) {
        String genericTypeClassName = extractClassName(getGenericType(field));
        return intentName + ".put" + genericTypeClassName + "ArrayListExtra(" + dataName + ", " + variableName + ");";
    }

    @Override
    public String getValue(PsiField field, String intentName, String dataName) {
        String genericTypeName = getGenericType(field);
        String genericTypeClassName = extractClassName(genericTypeName);
        return "this." + field.getName() + " = " + intentName + ".get" + genericTypeClassName + "ArrayListExtra(" + dataName + ");";
    }

    @NotNull
    private String getGenericType(PsiField field) {
        String genericType = "";
        try {
            PsiType[] parameters = ((PsiClassReferenceType) field.getType()).getParameters();
            if (parameters.length > 0) {
                genericType = parameters[0].getCanonicalText();
            }
        } catch (Exception ignored) {
        }
        return genericType;
    }

    private String extractClassName(String name) {
        int index = name.lastIndexOf('.');
        if (index < 0) {
            return name;
        } else {
            return name.substring(index + 1);
        }
    }


}
