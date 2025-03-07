/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2024 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jkiss.dbeaver.ui;

import org.eclipse.jface.wizard.IWizard;
import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.model.DBPDataSourceContainer;
import org.jkiss.dbeaver.model.app.DBPDataSourceRegistry;
import org.jkiss.dbeaver.model.connection.DBPDriver;
import org.jkiss.dbeaver.model.rcp.RCPProject;
import org.jkiss.dbeaver.model.runtime.DBRRunnableContext;

/**
 * IDataSourceConnectionEditorSite
 */
public interface IDataSourceConnectionEditorSite
{
    DBRRunnableContext getRunnableContext();

    DBPDataSourceRegistry getDataSourceRegistry();

    boolean isNew();

    DBPDriver getDriver();

    @NotNull
    DBPDataSourceContainer getActiveDataSource();

    void updateButtons();

    boolean openDriverEditor();

    boolean openSettingsPage(String pageId);

    void testConnection();

    RCPProject getProject();

    IWizard getWizard();

    /**
     * Fires property change event in all connection pages
     */
    void firePropertyChange(Object source, String property, Object oldValue, Object newValue);
}
