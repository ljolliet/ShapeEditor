package ui.javafx;

import ui.ColorPicker;
import ui.Mediator;

public class ColorPickerJFx extends javafx.scene.control.ColorPicker implements ColorPicker {
    private Mediator mediator;

    public ColorPickerJFx() {
        super();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "colorPicker";
    }
}
