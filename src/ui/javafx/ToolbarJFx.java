package ui.javafx;

import editor.core.Editor;
import editor.shapes.ShapeI;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import ui.ApplicationI;
import ui.Component;
import ui.DragAndDropArea;
import ui.Mediator;

public class ToolbarJFx extends GridPane implements Component {

    private Mediator mediator;

    public ToolbarJFx(){
        super();
        this.setOnDragDetected(this::onDragDetected);
        this.setOnMouseDragReleased(this::setOnMouseDragReleased);
    }

    private void onDragDetected(MouseEvent event) {
        System.out.println("[TOOLBAR] Drag detected");
        startFullDrag();

        // Detect left click
        if (event.getButton() == MouseButton.PRIMARY) {
            int pos = (int) ((event.getY() - this.getPadding().getTop()) / ApplicationI.TOOLBAR_CELL_HEIGHT);

            if (pos >= 0 && pos < this.getChildren().size()) {
                ShapeI shape = Editor.getInstance().getToolbar().getShapes().get(pos);
                mediator.dragFromToolbar(shape);
            }
        }
    }

    private void setOnMouseDragReleased(MouseEvent event) {
        System.out.println("[TOOLBAR] Drag released");

        // Detect left click
        if (event.getButton() == MouseButton.PRIMARY)
            if (Editor.getInstance().getShapeDragged() != null)
                mediator.dropInToolbar();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Toolbar";
    }
}
