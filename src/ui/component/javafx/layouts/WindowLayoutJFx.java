package ui.component.javafx.layouts;

import javafx.scene.layout.VBox;
import ui.component.Layout;
import ui.Mediator;

public class WindowLayoutJFx extends VBox implements Layout {

    private Mediator mediator;

    public WindowLayoutJFx(){
        this.toFront();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "WindowLayout";
    }
}
