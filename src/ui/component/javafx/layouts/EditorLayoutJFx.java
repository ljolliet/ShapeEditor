package ui.component.javafx.layouts;

import javafx.scene.layout.HBox;
import ui.component.Layout;
import ui.Mediator;

public class EditorLayoutJFx extends HBox implements Layout {

    private Mediator mediator;

    public EditorLayoutJFx(){
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "EditorLayout";
    }
}
