/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2018 Serge Rider (serge@jkiss.org)
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
/*
 * Created on Jul 13, 2004
 */
package org.jkiss.dbeaver.ext.erd.figures;

import org.eclipse.draw2d.*;
import org.jkiss.code.NotNull;
import org.jkiss.code.Nullable;
import org.jkiss.dbeaver.ext.erd.model.ERDDecorator;
import org.jkiss.dbeaver.ext.erd.model.ERDEntityAttribute;
import org.jkiss.dbeaver.ext.erd.part.AttributePart;
import org.jkiss.dbeaver.model.DBPImage;
import org.jkiss.dbeaver.ui.DBeaverIcons;

import java.util.List;

/**
 * Figure used to hold the column labels
 * @author Serge Rider
 */
public class AttributeItemFigure extends Figure
{
    private final AttributePart part;

	public AttributeItemFigure(AttributePart part)
	{
        super();
        this.part = part;

        ToolbarLayout layout = new ToolbarLayout(true);

        setLayoutManager(layout);

        boolean showCheckboxes = part.getDiagramPart().getDiagram().getDecorator().showCheckboxes();
        if (showCheckboxes) {
            CustomCheckBoxFigure attrCheckbox = new CustomCheckBoxFigure();
            add(attrCheckbox);
        }
        EditableLabel attrNameLabel = new EditableLabel(part.getAttribute().getName());
        DBPImage labelImage = part.getAttribute().getLabelImage();
        if (labelImage != null) {
            attrNameLabel.setIcon(DBeaverIcons.getImage(labelImage));
        }
        add(attrNameLabel);
	}

    public ERDEntityAttribute getAttribute() {
        return part.getAttribute();
    }

    @Nullable
    public CustomCheckBoxFigure getCheckBox() {
	    if (getChildren().size() < 2) {
	        return null;
        }
        return (CustomCheckBoxFigure) getChildren().get(0);
    }

    @NotNull
    public EditableLabel getLabel() {
        List children = getChildren();
        return (EditableLabel) children.get(children.size() == 1 ? 0 : 1);
    }

}
