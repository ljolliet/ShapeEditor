package ui.component.javafx.dialog;

import editor.edition.GroupEditionDialog;

public class GroupEditJFx extends EditDialogJFx {
    public GroupEditJFx(GroupEditionDialog groupED) {
        super();
        super.addColorToGridPane(groupED);
        super.addPositionToGridPane(groupED);
        super.addRotationToGridPane(groupED);
        super.addTranslationToGridPane(groupED);
        super.addRotationCenterToGridPane(groupED);
    }
}
