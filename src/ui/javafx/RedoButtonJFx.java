package ui.javafx;

public class RedoButtonJFx extends ButtonJFx {

    public RedoButtonJFx() {
        super("", REDO_RES);
    }

    @Override
    public String getName() {
        return "RedoButton";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.redo();
    }

}
