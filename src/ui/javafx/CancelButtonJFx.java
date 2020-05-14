package ui.javafx;

public class CancelButtonJFx extends ButtonJFx {
    public CancelButtonJFx() {
        super("Cancel");
    }

    @Override
    public String getName() {
        return "CancelButton";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.cancelEdit();
    }
}
