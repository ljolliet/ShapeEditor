package ui.javafx;

import javafx.scene.layout.GridPane;
import ui.DialogBox;
import ui.Mediator;

public class EditDialogJFx extends GridPane implements DialogBox {
    private Mediator mediator;

    public EditDialogJFx(){
        super();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "EditDialog";
    }
}
