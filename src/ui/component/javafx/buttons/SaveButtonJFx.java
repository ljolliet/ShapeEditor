package ui.component.javafx.buttons;

public class SaveButtonJFx extends ButtonJFx {

    public SaveButtonJFx() {
        super("", SAVE_RES);
    }

    @Override
    public String getName() {
        return "SaveButton";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.save();
    }
}
