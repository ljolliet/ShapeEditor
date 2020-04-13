package editor;

import editor.utils.Color;
import editor.utils.Point2D;
import ui.Rendering;

import java.util.Set;

public interface Shape extends Cloneable {
    void addShape(Shape s);
    void removeShape(Shape s);
    Set<Shape> getChild();
    void collapse(); // to collapse a group
    Shape clone() throws CloneNotSupportedException;

    void drawInScene(Rendering rendering);
    void drawInToolbar(Rendering rendering);

    boolean contains(Point2D position);
    void setPosition(Point2D pos);

    void setColor(Color color);

    //TODO add Memento
}
