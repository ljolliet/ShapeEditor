package editor;

import java.util.Set;

public interface Shape extends Cloneable{
    void addShape(Shape s);
    void removeShape(Shape s);
    Set<Shape> getChild();
    void collapse(); // to collapse a group
    Shape clone() throws CloneNotSupportedException;
    //TODO add Memento
    //TODO add Observable
}
