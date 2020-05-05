package ui.javafx;

import javafx.scene.layout.GridPane;
import ui.DragAndDropArea;
import ui.Mediator;

public class ToolBarJFx extends GridPane implements DragAndDropArea {

    private Mediator mediator;

    public ToolBarJFx(){
        super();
        this.setOnDragDetected(mouseEvent -> onClick());
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onDrag() {

    }

    @Override
    public void onDrop() {

    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "ToolBar";
    }
}
