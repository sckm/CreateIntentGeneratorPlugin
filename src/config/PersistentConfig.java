package config;/*
 * Copyright (C) 2016 Seesaa Inc.
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

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StorageScheme;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;

import org.jetbrains.annotations.Nullable;

@State(
        name = "com.scache.createintentmethodgenerator.config.PersistentConfig",
        reloadable = true,
        storages = {
                @Storage(id = "default", file = "$PROJECT_FILE$"),
                @Storage(id = "dir", file = "$PROJECT_CONFIG_DIR$/createintentgenerator_plugin.xml", scheme = StorageScheme.DIRECTORY_BASED)
        }
)

public class PersistentConfig implements PersistentStateComponent<PersistentConfig> {
    private boolean isUseVariableName;

    @Nullable
    public static PersistentConfig getInstance(Project project) {
        return ServiceManager.getService(project, PersistentConfig.class);
    }

    @Nullable
    @Override
    public PersistentConfig getState() {
        return this;
    }

    @Override
    public void loadState(PersistentConfig persistentConfig) {
        XmlSerializerUtil.copyBean(persistentConfig, this);
    }

    public boolean isUseVariableName() {
        return isUseVariableName;
    }

    public void setUseVariableName(boolean useVariableName) {
        isUseVariableName = useVariableName;
    }
}
