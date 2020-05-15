package ui;

import editor.shapes.ShapeI;
import editor.utils.Point2D;
import ui.component.Component;

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
    void dragFromScene(Point2D position);
    void dropInToolbar();
    void dropInScene(Point2D position);
    void dropInTrash();

    // Selection
    void selectShape(ShapeI shape);
    void toggleSelectedShape(ShapeI shape);
    void startSelection(Point2D startPoint);
    void moveSelection(Point2D point);
    void stopSelection(List<ShapeI> shapes);

    // Edition dialog
    void showMenu(ShapeI shape, Point2D position);
    void showContextMenu(List<ShapeI> shapes, Point2D position);
    void cancelEdit();
    void applyEdit();
    void applyAndQuitEdit();

    // Shadow shape
    void moveShadowShape(Point2D position);
    void clearShadowShape();

    void clearEditorActions();
}
