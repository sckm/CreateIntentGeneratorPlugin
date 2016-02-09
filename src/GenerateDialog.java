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
import com.intellij.ui.components.panels.VerticalLayout;

import org.jetbrains.annotations.Nullable;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GenerateDialog extends DialogWrapper implements OptionStateGetter{

    private final LabeledComponent<JPanel> myComponent;
    private CollectionListModel<PsiField> myFields;

    private JBList fieldList;
    private JCheckBox useVariableNameCheckBox;

    protected GenerateDialog(PsiClass psiClass) {
        super(psiClass.getProject());
        setTitle("Select Fields for createIntent Method Generation");

        JPanel panel = createFiledListPanel(psiClass);
        JPanel optionPanel = createOptionPanel();

        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        root.add(panel, BorderLayout.CENTER);
        root.add(optionPanel, BorderLayout.PAGE_END);
        myComponent = LabeledComponent.create(root, "Fields to include in Intent");

        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myComponent;
    }

    private JPanel createFiledListPanel(PsiClass psiClass){
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
        return decorator.createPanel();
    }

    private JPanel createOptionPanel(){
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        useVariableNameCheckBox = new JCheckBox();
        useVariableNameCheckBox.setText("use variable name for constant value");
        optionPanel.add(useVariableNameCheckBox);

        return optionPanel;
    }

    public List<PsiField> getSelectedFields() {
        List<PsiField> selectedFields = new ArrayList<PsiField>();
        for(int index : fieldList.getSelectedIndices()){
            selectedFields.add(myFields.getElementAt(index));
        }

        return selectedFields;
    }

    @Override
    public boolean isUseVariableName() {
        return useVariableNameCheckBox.isSelected();
    }
}