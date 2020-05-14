package ui.javafx;

public class OkButtonJFx extends ButtonJFx {
    public OkButtonJFx() {
        super("OK");
    }

    @Override
    public String getName() {
        return "OkButton";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.applyAndQuitEdit();
    }
}
