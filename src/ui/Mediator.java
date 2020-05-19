/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui;

import editor.shapes.Shape;
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
    void dragFromToolbar(Shape shape);
    void dragFromScene(Point2D position);
    void dropInToolbar();
    void dropInScene(Point2D position);
    void dropInTrash();

    // Selection
    void selectShape(Shape shape);
    void toggleSelectedShape(Shape shape);
    void startSelection(Point2D startPoint);
    void moveSelection(Point2D point);
    void stopSelection(List<Shape> shapes);

    // Edition dialog
    void showMenu(Shape shape, Point2D position);
    void showContextMenu(List<Shape> shapes, Point2D position);
    void cancelEdit();
    void applyEdit();
    void applyAndQuitEdit();

    // Shadow shape
    void moveShadowShape(Point2D position);
    void clearShadowShape();

    void clearEditorActions();
}
