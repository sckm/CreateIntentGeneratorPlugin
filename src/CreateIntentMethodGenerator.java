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

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.List;

public class CreateIntentMethodGenerator extends BaseGenerateAction {
    private static final String ANDROID_ACTIIVITY_QUALIFIED_NAME = "android.app.Activity";
    public CreateIntentMethodGenerator() {
        super(null);
    }

    public CreateIntentMethodGenerator(CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psi = getPsiClassFromContext(e);

        GenerateDialog dialog = new GenerateDialog(psi);
        dialog.show();

        if (dialog.isOK()) {
            generateCreateIntentMethod(psi, dialog.getSelectedFields());
        }
    }

    @Override
    protected boolean isValidForClass(PsiClass targetClass) {
        // handle only Activity class
        PsiClass superClass = targetClass;
        while (superClass != null) {
            if(ANDROID_ACTIIVITY_QUALIFIED_NAME.equals(superClass.getQualifiedName())){
                return true;
            }
            superClass = superClass.getSuperClass();
        }
        return false;
    }

    private void generateCreateIntentMethod(final PsiClass psiClass, final List<PsiField> fields) {
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                new CodeGenerator(psiClass, fields).generate();
            }
        }.execute();
    }

    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if (psiFile == null || editor == null) {
            return null;
        }

        int offset = editor.getCaretModel().getOffset();
        PsiElement element = psiFile.findElementAt(offset);

        return PsiTreeUtil.getParentOfType(element, PsiClass.class);
    }
}
