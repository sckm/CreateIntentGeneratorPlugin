/*
 * Copyright (c) 2015 Seesaa Inc.
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

import typegenerators.TypeGenerator;

public abstract class AbstractTypeGenerator implements TypeGenerator {
    @Override
    public String declareValue(PsiField field, String variableName) {
        return field.getType().getCanonicalText() + " " + variableName;
    }

    @Override
    public String putValue(PsiField field, String intentName, String dataName, String variableName) {
        return intentName + ".putExtra(" + dataName + ", " + variableName + ");";
    }
}
