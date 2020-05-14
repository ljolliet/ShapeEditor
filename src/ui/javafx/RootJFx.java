package ui.javafx;

import editor.core.Editor;
import editor.shapes.ShapeI;
import editor.utils.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ui.Component;
import ui.Mediator;

import java.util.ArrayList;
import java.util.List;

public class RootJFx extends Group implements Component {

    private Mediator mediator;

    public RootJFx(){
        super();
        this.setOnMousePressed(this::onMousePressed);
        this.setOnDragDetected(this::onDragDetected);
        this.setOnMouseDragged(this::onMouseDragged);
        this.setOnMouseDragReleased(this::onMouseDragReleased);
        this.setOnMouseReleased(this::onMouseReleased);
    }

    private void onMousePressed(MouseEvent event) {
        mediator.clearEditorActions();

        ShapeI shape = null;
        Point2D coords = new Point2D(event.getX(), event.getY());

        // Look if the mouse is on an existing shape
        for (ShapeI s : Editor.getInstance().getScene().getShapes()) {
            if (s.contains(coords)) { // Found
                shape = s;
                break;
            }
        }

        // Detect left click
        if (event.getButton() == MouseButton.PRIMARY) {
            if (shape != null) {
                if (event.isShortcutDown()) // CTRL for Windows, CMD for Mac, etc
                    mediator.toggleSelectedShape(shape);
                else {
                    // Look if the clicked shape is contained
                    // into the selected shapes
                    boolean here = false;
                    for (ShapeI s: Editor.getInstance().getScene().getSelectedShapes())
                        if (shape.equals(s) || s.containsChild(shape)) {
                            here = true;
                            break;
                        }

                    if (!here)
                        mediator.selectShape(shape);
                }

                if (Editor.getInstance().getScene().getSelectedShapes().size() > 0)
                    mediator.dragFromScene(new Point2D(event.getX(), event.getY()));
            }
            else {
                // Clear selection
                mediator.stopSelection(new ArrayList<>());
                // Begin a new one
                mediator.startSelection(new Point2D(event.getX(), event.getY()));
            }
        }
        // Right click
        else if (event.getButton() == MouseButton.SECONDARY) {
            // Click on a selection
            List<ShapeI> shapes = Editor.getInstance().getScene().getSelectedShapes();
            if (shapes.size() > 1) {
                mediator.showContextMenu(shapes, new Point2D(event.getScreenX(), event.getScreenY()));
            }
            else if (shape != null) {
                mediator.selectShape(shape);
                mediator.showEditionDialog(shape, new Point2D(event.getScreenX(), event.getScreenY()));

            }
        }
    }

    private void onDragDetected(MouseEvent event) {
        System.out.println("[ROOT] Drag detected");
        startFullDrag();
        Editor.getInstance().getRendering().drawEditor();
    }

    private void onMouseDragged(MouseEvent event) {
        // Detect left click
        if (event.getButton() == MouseButton.PRIMARY)
            if (Editor.getInstance().getShapeDragged() == null)
                mediator.moveSelection(new Point2D(event.getX(), event.getY()));
    }

    private void onMouseDragReleased(MouseEvent event) {
        System.out.println("[ROOT] Drag released");

        // If there is a shape dragged
        if (Editor.getInstance().getShapeDragged() != null) {
            mediator.dropInScene(new Point2D(event.getX(), event.getY()));
        }
    }

    private void onMouseReleased(MouseEvent event) {
        // If there is a selection action
        if (event.getButton() == MouseButton.PRIMARY) {
            if (Editor.getInstance().getSelectionShape().isOn()) {
                ArrayList<ShapeI> selectedShapes = new ArrayList<>();

                // Add all selected shapes in array
                for (ShapeI shape : Editor.getInstance().getScene().getShapes())
                    if (shape.contained(Editor.getInstance().getSelectionShape()))
                        selectedShapes.add(shape);
                mediator.stopSelection(selectedShapes);
            }

            Editor.getInstance().setShapeDragged(null);
            Editor.getInstance().getRendering().drawEditor();
        }
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Root";
    }
}
