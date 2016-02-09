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

import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PropertyUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import typegenerators.ChainGeneratorFactory;
import typegenerators.ListGeneratorFactory;
import typegenerators.ObjectArrayGeneratorFactory;
import typegenerators.ObjectTypeGeneratorFactory;
import typegenerators.ParcelableGeneratorFactory;
import typegenerators.PrimitiveArrayGeneratorFactory;
import typegenerators.PrimitiveTypeGeneratorFactory;
import typegenerators.SerializableGeneratorFactory;
import typegenerators.TypeGenerator;
import typegenerators.TypeGeneratorFactory;
import util.VariableNameUtils;

public class CodeGenerator {
    private static final String INTENT_QUALIFIED_NAME = "android.content.Intent";
    private static final String CONTEXT_QUALIFIED_NAME = "android.content.Context";

    private final PsiClass mClass;
    private final List<PsiField> mFields;
    private final boolean mIsUseVariableName;
    private final TypeGeneratorFactory mTypeGeneratorFactory;

    public CodeGenerator(PsiClass aClass, List<PsiField> fields, boolean isUseVariableName) {
        mClass = aClass;
        mFields = fields;
        mIsUseVariableName = isUseVariableName;

        this.mTypeGeneratorFactory = new ChainGeneratorFactory(
                new PrimitiveTypeGeneratorFactory(),
                new PrimitiveArrayGeneratorFactory(),
                new ObjectArrayGeneratorFactory(),
                new ObjectTypeGeneratorFactory(),
                // ArrayList<T extends Parcelable>を処理するためにListGeneratorFactoryより前にParcelableGeneratorFactoryを置いておく
                new ParcelableGeneratorFactory(),
                new ListGeneratorFactory(),
                // Stringなどが引っかかってしまうのでParcelable, Serializableは一番最後にしておく
                new SerializableGeneratorFactory()
        );
    }

    public void generate() {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mClass.getProject());

        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(mClass.getProject());

        PsiMethod createIntentMethod = elementFactory.createMethodFromText(generateCreateIntent(mFields, mClass), mClass);
        styleManager.shortenClassReferences(mClass.addBefore(createIntentMethod, mClass.getLastChild()));

        PsiMethod restoreMethod = elementFactory.createMethodFromText(generateRestoreFromIntent(mFields, mClass), mClass);
        styleManager.shortenClassReferences(mClass.addBefore(restoreMethod, mClass.getLastChild()));
    }

    private String generateCreateIntent(List<PsiField> fields, PsiClass psiClass) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mClass.getProject());

        // method signature
        StringBuilder sb = new StringBuilder(
                String.format("public static %s createIntent(%s context", INTENT_QUALIFIED_NAME, CONTEXT_QUALIFIED_NAME));
        for (PsiField field : fields) {
            sb.append(", ").append(getSerializerForType(field).declareValue(field, fieldToLocalVariableName(field)));
        }

        sb.append("){");

        // method body
        String intentName = "intent";
        sb.append(INTENT_QUALIFIED_NAME)
                .append(" ")
                .append(intentName)
                .append(" = new Intent(context, ")
                .append(psiClass.getName())
                .append(".class);");

        PsiField[] fields1 = mClass.getFields();
        PsiElement lastPsiElement = fields.size() == 0 ? mClass.getLastChild() : fields1[0];

        for (PsiField field : fields) {
            TypeGenerator gene = getSerializerForType(field);
            String dataConstName = fieldToConstName(field);
            sb.append(gene.putValue(field, intentName, dataConstName, fieldToLocalVariableName(field)))
                    .append('\n');

            PsiField creatorField = elementFactory.createFieldFromText(generateArgConst(mClass, field, dataConstName), mClass);
            lastPsiElement = JavaCodeStyleManager.getInstance(mClass.getProject()).shortenClassReferences(mClass.addBefore(creatorField, lastPsiElement));
        }

        sb.append("return ").append(intentName).append(";\n}");
        return sb.toString();
    }

    private String generateRestoreFromIntent(List<PsiField> fields, PsiClass psiClass) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mClass.getProject());
        String intentName = "intent";

        StringBuilder sb = new StringBuilder();
        sb.append("private void restoreFromIntent(Intent ")
                .append(intentName)
                .append("){");

        for (PsiField field : fields) {
            String dataConstName = fieldToConstName(field);
            sb.append(getSerializerForType(field).getValue(field, intentName, dataConstName));
        }

        sb.append("\n}");

        return sb.toString();
    }

    private String generateArgConst(PsiClass psiClass, PsiField psiField, String dataConstName) {
        String argConstValue = mIsUseVariableName
                ? psiField.getName()
                : dataConstName.toLowerCase();
        return String.format("private static final String %s = \"%s\";", dataConstName, argConstValue);
    }

    private String getPackageName(@NotNull PsiClass psiClass) {
        String packageName;
        try {
            PsiJavaFile javaFile = (PsiJavaFile) psiClass.getContainingFile();
            packageName = javaFile.getPackageName();
        } catch (Exception e) {
            packageName = "";
        }
        return packageName;
    }

    private TypeGenerator getSerializerForType(PsiField field) {
        return mTypeGeneratorFactory.getGenerator(field.getType());
    }

    // TODO スネークケースの文字が_で始まる場合 _が２こ連続してしまうので修正する
    private String fieldToConstName(PsiField field) {
        return "ARG_" + VariableNameUtils.camelToSneakCase(PropertyUtil.suggestPropertyName(field)).toUpperCase();
    }

    private String fieldToLocalVariableName(PsiField field) {
        return PropertyUtil.suggestPropertyName(field);
    }

}
