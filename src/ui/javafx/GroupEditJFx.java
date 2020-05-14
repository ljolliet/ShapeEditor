package ui.javafx;

import editor.edition.GroupeEditionDialog;

public class GroupEditJFx extends EditDialogJFx {
    public GroupEditJFx(GroupeEditionDialog groupED, OkButtonJFx okButton, ApplyButtonJFx applyButton, CancelButtonJFx cancelButton) {
        super(okButton, applyButton, cancelButton);
        super.addColorToGridPane(groupED);
        super.addPositionToGridPane(groupED);
        super.addRotationToGridPane(groupED);
    }
}
