package editor.shapes;

import editor.observer.Observable;

import java.util.HashSet;

public abstract class Shape extends Observable implements ShapeI {

    @Override
    public ShapeI clone() throws CloneNotSupportedException {
        Shape clone = (Shape) super.clone();
        clone.setObservers(new HashSet<>());

        return clone;
    }
}
