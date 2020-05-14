package ui.javafx;

import editor.edition.GroupEditionDialog;

public class GroupEditJFx extends EditDialogJFx {
    public GroupEditJFx(GroupEditionDialog groupED) {
        super();
        super.addColorToGridPane(groupED);
        super.addPositionToGridPane(groupED);
        super.addRotationToGridPane(groupED);
    }
}
