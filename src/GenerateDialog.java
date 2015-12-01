/*
 * Copyright (C) 2015 Seesaa Inc.
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

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GenerateDialog extends DialogWrapper {

    private final LabeledComponent<JPanel> myComponent;
    private CollectionListModel<PsiField> myFields;

    private JBList fieldList;

    protected GenerateDialog(PsiClass psiClass) {
        super(psiClass.getProject());
        setTitle("Select Fields for createIntent Method Generation");

        PsiField[] allFields = psiClass.getFields();
        PsiField[] fields = new PsiField[allFields.length];

        int i = 0;

        for (PsiField field : allFields) {
            // Exclude static fields
            if (!field.hasModifierProperty(PsiModifier.STATIC)) {
                fields[i++] = field;
            }
        }

        // i is post-incremented, so no need to add 1 for the count
        fields = Arrays.copyOfRange(fields, 0, i);

        myFields = new CollectionListModel<PsiField>(fields);

        fieldList = new JBList(myFields);
        fieldList.setCellRenderer(new DefaultPsiElementCellRenderer());
        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(fieldList);
        decorator.disableAddAction();
        JPanel panel = decorator.createPanel();

        myComponent = LabeledComponent.create(panel, "Fields to include in Intent");

        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myComponent;
    }

    public List<PsiField> getSelectedFields() {
        List<PsiField> selectedFields = new ArrayList<>();
        for(int index : fieldList.getSelectedIndices()){
            selectedFields.add(myFields.getElementAt(index));
        }

        return selectedFields;
    }
}