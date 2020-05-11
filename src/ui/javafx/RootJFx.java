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

// TODO Test with a Pane
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

        // Detect left click
        if (event.getButton() == MouseButton.PRIMARY) {
            boolean inShape = false;
            Point2D coords = new Point2D(event.getX(), event.getY());

            // Look if the mouse is on an existing shape
            for (ShapeI shape : Editor.getInstance().getScene().getShapes()) {
                if (shape.contains(coords)) { // Found
                    mediator.dragFromScene(shape, coords);
                    inShape = true;
                    break;
                }
            }

            // If no shape found --> start select action
            if (!inShape)
                mediator.startSelection(new Point2D(event.getX(), event.getY()));
        }
        // Right click
        else if (event.getButton() == MouseButton.SECONDARY) {
            // Click on a selection
            if (Editor.getInstance().getScene().getSelectedShapes().size() > 1) {
                //TODO : not a group but a list of shape //mediator.showGroupEditionDialog(new Point2D(event.getScreenX(), event.getScreenY()));
            }
            // Click on a single shape
            else {
                // Look for the clicked shape
                for (ShapeI shape : Editor.getInstance().getScene().getShapes())
                    if (shape.contains(new Point2D(event.getX(), event.getY()))) // Found
                        mediator.showEditionDialog(shape, new Point2D(event.getScreenX(), event.getScreenY()));
            }
        }
    }

    private void onDragDetected(MouseEvent event) {
        System.out.println("[ROOT] Drag detected");
        startFullDrag();
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
        if (Editor.getInstance().getSelectionShape().isOn()) {
            ArrayList<ShapeI> selectedShapes = new ArrayList<>();

            // Add all selected shapes in array
            for (ShapeI shape : Editor.getInstance().getScene().getShapes())
                if (shape.contained(Editor.getInstance().getSelectionShape()))
                    selectedShapes.add(shape);

            mediator.stopSelection(selectedShapes);
        }
        else {
            mediator.stopSelection(new ArrayList<>());
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
