/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component.javafx.dialog;

import editor.edition.GroupEditionDialog;

public class GroupEditJFx extends EditDialogJFx {
    public GroupEditJFx(GroupEditionDialog groupED) {
        super(groupED);
        this.setButtons();
    }
}
