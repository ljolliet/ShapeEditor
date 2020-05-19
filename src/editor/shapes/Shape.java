package editor.shapes;

import editor.edition.EditionDialogI;
import editor.core.EditorVisitor;
import editor.observer.ObservableI;
import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.SelectionShape;
import editor.utils.Vec2D;
import ui.Rendering;

import java.io.Serializable;
import java.util.Set;

public interface Shape extends Cloneable, Serializable, ObservableI {
    double MIN_SIZE = 1.;
    double MAX_ROTATION = 360.;
    double MIN_ROTATION = 0.;
    int MAX_SIDE_LENGTH = 100;
    int MIN_SIDE_LENGTH = 0;
    int MAX_NB_SIDE = 30;
    int MIN_NB_SIDE = 3;
    int MAX_BORDER_RADIUS = 50;
    int MIN_BORDER_RADIUS = 0;

    void addShape(Shape s);
    void removeShape(Shape s);
    Set<Shape> getChildren();
    void setChildren(Set<Shape> shapes);

    Shape clone();
    void drawInScene(Rendering rendering);
    void drawInToolbar(Rendering rendering);

    boolean containsChild(Shape s);
    boolean contains(Point2D position);
    boolean contained(SelectionShape s);

    Point2D[] getPoints();
    void setPosition(Point2D pos);
    void setColor(Color color);
    void setRotation(double angle);
    void setRotationCenter(Point2D pos);
    void setTranslation(Vec2D translation);
    void setAllValues(Point2D position, Color color, double rotation, Vec2D translation, Point2D rotationCenter);

    Point2D getPosition();
    Color getColor();
    double getRotation();
    Point2D getRotationCenter();
    Vec2D getTranslation();

    EditionDialogI createEditionDialog();

    void accept(EditorVisitor visitor);
}
