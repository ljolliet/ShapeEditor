package ui.component.javafx.buttons;

public class OpenButtonJFx extends ButtonJFx {

    public OpenButtonJFx() {
        super("", OPEN_RES);
    }

    @Override
    public String getName() {
        return "OpenButton";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.open();
    }
}
