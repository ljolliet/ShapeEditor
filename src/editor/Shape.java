package editor;

import ui.Rendering;

import java.util.Set;

public interface Shape extends Cloneable{
    void addShape(Shape s);
    void removeShape(Shape s);
    Set<Shape> getChild();
    void collapse(); // to collapse a group
    Shape clone() throws CloneNotSupportedException;
    void setRendering(Rendering r);
    void draw(Object context);

    //TODO add Memento
    //TODO add Observable
}
