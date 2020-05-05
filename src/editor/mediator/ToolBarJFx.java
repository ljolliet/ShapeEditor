package editor.mediator;

import javafx.scene.layout.GridPane;

public class ToolBarJFx extends GridPane implements DragAndDropArea{

    ToolBarJFx(){
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
}
