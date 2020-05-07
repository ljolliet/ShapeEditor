package ui.javafx;

import editor.core.Editor;
import editor.edition.EditionDialogI;
import editor.shapes.Shape;
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
        this.setOnMouseDragReleased(this::setOnMouseDragReleased);
    }

    private void onMousePressed(MouseEvent event) {

        // rendering.hideEditionDialog(); // TODO

        // Detect left click
        if (event.getButton() == MouseButton.PRIMARY) {
            boolean inShape = false;

            // Look if the mouse is on an existing shape
            for (ShapeI shape : Editor.getInstance().getScene().getShapes()) {
                if (shape.contains(new Point2D(event.getX(), event.getY()))) { // Found
                    mediator.dragFromScene(shape);
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
                mediator.showGroupEditionDialog(new Point2D(event.getScreenX(), event.getScreenY()));
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

    private void setOnMouseDragReleased(MouseEvent event) {
        System.out.println("[ROOT] Drag released");

        System.out.println(new Point2D(event.getX(), event.getY()));

        // If there is a shape dragged
        if (Editor.getInstance().getShapeDragged() != null) {
            mediator.dropInScene(new Point2D(event.getX(), event.getY()));
        }
        else if (Editor.getInstance().getSelectionShape() != null) {
            ArrayList<Shape> selectedShapes = new ArrayList<>();

            // Add all selected shapes in array
            for (Shape shape : Editor.getInstance().getScene().getShapes())
                if (shape.contained(Editor.getInstance().getSelectionShape()))
                    selectedShapes.add(shape);

            mediator.stopSelection(selectedShapes);
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
