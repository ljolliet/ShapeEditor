import java.util.Set;

public interface Shape{
    void addShape(Shape s);
    void removeShape(Shape s);
    Set<Shape> getChild();
    void collapse(); // to collapse a group
    //TODO add prototype
    //TODO add Memento
    //TODO add Observable
}
