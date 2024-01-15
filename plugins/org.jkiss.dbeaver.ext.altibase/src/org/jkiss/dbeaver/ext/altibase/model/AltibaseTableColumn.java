/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2023 DBeaver Corp and others
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
package org.jkiss.dbeaver.ext.altibase.model;

import org.jkiss.dbeaver.ext.generic.model.GenericTableBase;
import org.jkiss.dbeaver.ext.generic.model.GenericTableColumn;
import org.jkiss.dbeaver.model.DBPNamedObject2;
import org.jkiss.dbeaver.model.meta.Property;

public class AltibaseTableColumn extends GenericTableColumn implements DBPNamedObject2 {

    public AltibaseTableColumn(GenericTableBase table) {
        super(table);
    }

    public AltibaseTableColumn(
            GenericTableBase table,
            String columnName,
            String typeName,
            int valueType,
            int sourceType,
            int ordinalPosition,
            long columnSize,
            long charLength,
            Integer scale,
            Integer precision,
            int radix,
            boolean notNull,
            String remarks,
            String defaultValue,
            boolean autoIncrement,
            boolean autoGenerated) {
        super(table,
                columnName,
                typeName,
                valueType,
                sourceType,
                ordinalPosition,
                columnSize,
                charLength,
                scale,
                precision,
                radix,
                notNull,
                remarks,
                defaultValue,
                autoIncrement,
                autoGenerated);
    }

    /*
     * No auto-generated or auto-increment table columns in Altibase
     */
    @Override
    @Property(viewable = false, order = 51)
    public boolean isAutoGenerated() {
        return autoGenerated;
    }

    @Override
    @Property(viewable = false, hidden = true, order = 52)
    public boolean isAutoIncrement() {
        return super.isAutoIncrement();
    }
    
    /**
     * Whether the column has its own comment or not. 
     */
    public boolean hasComment() {
        return (this.getDescription() != null && this.getDescription().length() > 0);
    }
}