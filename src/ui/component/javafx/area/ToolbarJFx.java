/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component.javafx.area;

import editor.core.Editor;
import editor.shapes.Shape;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import ui.ApplicationI;
import ui.Mediator;
import ui.component.DragAndDropArea;

import static ui.ApplicationI.TOOLBAR_SPACING;

public class ToolbarJFx extends GridPane implements DragAndDropArea {

    private Mediator mediator;

    public ToolbarJFx(){
        super();
        this.setOnDragDetected(this::onDragDetected);
        this.setOnMouseDragReleased(this::setOnMouseDragReleased);
        this.setPadding(new Insets(TOOLBAR_SPACING));
        this.setAlignment(Pos.BASELINE_CENTER);
    }

    private void onDragDetected(MouseEvent event) {
        System.out.println("[TOOLBAR] Drag detected");
        startFullDrag();

        // Detect left click
        if (event.getButton() == MouseButton.PRIMARY) {
            int pos = (int) ((event.getY() - this.getPadding().getTop()) / ApplicationI.TOOLBAR_CELL_HEIGHT);

            if (pos >= 0 && pos < this.getChildren().size()) {
                Shape shape = Editor.getInstance().getToolbar().getShapes().get(pos);
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
