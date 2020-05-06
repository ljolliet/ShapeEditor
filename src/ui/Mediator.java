package ui;

import editor.shapes.Shape;
import editor.shapes.ShapeI;
import editor.utils.Point2D;

import java.util.List;

public interface Mediator {
    void registerComponent(Component component);
    //list of services
    void undo();
    void redo();
    void save();
    void open();

    void dragFromToolbar(ShapeI shape);
    void dragFromScene(ShapeI shape);
    void dropInToolbar();
    void dropInScene(Point2D coords);

    void startSelection(Point2D startPoint);
    void moveSelection(Point2D point);
    void stopSelection(List<Shape> shapes);
}
