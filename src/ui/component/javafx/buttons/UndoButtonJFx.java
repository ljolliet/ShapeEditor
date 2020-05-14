package ui.component.javafx.buttons;

public class UndoButtonJFx extends ButtonJFx{

    public UndoButtonJFx() {
        super("", UNDO_RES);
    }

    @Override
    public String getName() {
        return "UndoButton";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.undo();
    }

}
