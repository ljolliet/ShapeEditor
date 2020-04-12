package java.editor;

import java.editor.utils.Vec2D;
import java.ui.Rendering;

import java.util.Set;

public interface Shape extends Cloneable {
    void addShape(Shape s);
    void removeShape(Shape s);
    Set<Shape> getChild();
    void collapse(); // to collapse a group
    Shape clone() throws CloneNotSupportedException;

    void drawInScene(Rendering rendering);
    void drawInToolbar(Rendering rendering);

    boolean contains(Vec2D position);
    void setPosition(Vec2D pos);

    //TODO add Memento
}
