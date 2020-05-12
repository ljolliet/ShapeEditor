package editor.shapes;

import editor.edition.EditionDialogI;
import editor.core.EditorVisitor;
import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.SelectionShape;
import editor.utils.Vec2D;
import ui.Rendering;

import java.io.Serializable;
import java.util.Set;

public interface ShapeI extends Cloneable, Serializable {
    double MIN_SIZE = 1.;
    double MAX_ROTATION = 360.;
    double MIN_ROTATION = 0.;
    int MAX_RADIUS = 50;
    int MIN_RADIUS = 0;

    void addShape(ShapeI s);
    void removeShape(ShapeI s);
    Set<ShapeI> getChildren();
    void setChildren(Set<ShapeI> shapes);

    ShapeI clone();
    void drawInScene(Rendering rendering);
    void drawInToolbar(Rendering rendering);

    boolean containsChild(ShapeI s);
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

    void accept(EditorVisitor visitor);
}
