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
package org.jkiss.dbeaver.model.sql.semantics.model;

import org.jkiss.code.NotNull;
import org.jkiss.code.Nullable;
import org.jkiss.dbeaver.model.sql.semantics.SQLQueryRecognitionContext;
import org.jkiss.dbeaver.model.sql.semantics.SQLQuerySymbolEntry;
import org.jkiss.dbeaver.model.sql.semantics.context.SQLQueryDataContext;
import org.jkiss.dbeaver.model.stm.STMTreeNode;

import java.util.Collection;
import java.util.Set;

/**
 * Query model for recognition
 */
public class SQLQueryModel extends SQLQueryNodeModel {
    @NotNull
    private final Set<SQLQuerySymbolEntry> symbolEntries;
    @Nullable
    private final SQLQueryModelContent queryContent;

    
    public SQLQueryModel(
        @NotNull STMTreeNode syntaxNode,
        @Nullable SQLQueryModelContent queryContent,
        @NotNull Set<SQLQuerySymbolEntry> symbolEntries
    ) {
        super(syntaxNode.getRealInterval(), syntaxNode, queryContent);
        this.queryContent = queryContent;
        this.symbolEntries = symbolEntries;
    }

    @NotNull
    public Collection<SQLQuerySymbolEntry> getAllSymbols() {
        return symbolEntries;
    }

    @Nullable
    public SQLQueryModelContent getQueryModel() {
        return this.queryContent;
    }

    @Nullable
    @Override
    public SQLQueryDataContext getGivenDataContext() {
        return this.queryContent == null ? null : this.queryContent.getGivenDataContext();
    }

    @Nullable
    @Override
    public SQLQueryDataContext getResultDataContext() {
        return this.queryContent == null ? null : this.queryContent.getResultDataContext();
    }

    /**
     * Propagate semantics context and establish relations through the query model
     */
    public void propagateContext(@NotNull SQLQueryDataContext dataContext, @NotNull SQLQueryRecognitionContext recognitionContext) {
        if (this.queryContent != null) {
            this.queryContent.applyContext(dataContext, recognitionContext);
        }
    }

    /**
     * Returns nested node of the query model for the specified offset in the source text
     */
    public SQLQueryNodeModel findNodeContaining(int textOffset) {
        SQLQueryNodeModel node = this;
        SQLQueryNodeModel nested = node.findChildNodeContaining(textOffset); 
        while (nested != null) {
            node = nested;
            nested = nested.findChildNodeContaining(textOffset);
        }
        return node;
    }

    @Override
    protected <R, T> R applyImpl(@NotNull SQLQueryNodeModelVisitor<T, R> visitor, @NotNull T arg) {
        return visitor.visitSelectionModel(this, arg);
    }
}
