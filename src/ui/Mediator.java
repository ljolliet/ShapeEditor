package ui;

import editor.shapes.ShapeI;
import editor.utils.Point2D;

import java.util.List;

public interface Mediator {
    void registerComponent(Component component);

    // Option actions
    void undo();
    void redo();
    void save();
    void open();

    // Drag & drop
    void dragFromToolbar(ShapeI shape);
    void dragFromScene(ShapeI shape, Point2D coords);
    void dropInToolbar();
    void dropInScene(Point2D coords);
    void dropInTrash();

    // Selection
    void startSelection(Point2D startPoint);
    void moveSelection(Point2D point);
    void stopSelection(List<ShapeI> shapes);

    // Edition dialog
    void showEditionDialog(ShapeI shape, Point2D coords);
    void showGroupEditionDialog(Point2D point);

    // Shadow shape
    void moveShadowShape(Point2D coords);
    void clearShadowShape();

    void clearEditorActions();
}
