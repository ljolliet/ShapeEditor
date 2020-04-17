package editor;

import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.SelectionShape;
import editor.utils.Vec2D;
import editor.edition.EditionDialogI;
import ui.Rendering;

import java.util.Set;

public interface Shape extends Cloneable {
    void addShape(Shape s);
    void removeShape(Shape s);
    Set<Shape> getChild();
    void setChild(Set<Shape> shapes);
    void collapse(); // to collapse a group

    Shape clone() throws CloneNotSupportedException;
    void drawInScene(Rendering rendering);

    void drawInToolbar(Rendering rendering);
    boolean contains(Point2D position);
    boolean contained(SelectionShape s);

    Point2D[] getPoints();
    void setPosition(Point2D pos);
    void setColor(Color color);
    void setRotation(double angle);
    void setRotationCenter(Point2D pos);

    void setTranslation(Vec2D translation);
    Point2D getPosition();
    Color getColor();
    double getRotation();
    Point2D getRotationCenter();

    Vec2D getTranslation();

    EditionDialogI createEditionDialog();

    //TODO add Memento
}
