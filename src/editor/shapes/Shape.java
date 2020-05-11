package editor.shapes;

import editor.observer.Observable;

import java.util.HashSet;

public abstract class Shape extends Observable implements ShapeI {

    @Override
    public ShapeI clone() {
        Shape clone = null;
        try {
            clone = (Shape) super.clone();
            clone.setObservers(new HashSet<>());
        } catch (CloneNotSupportedException e) {
            // Never happens because extends Cloneable
            e.printStackTrace();
        }

        return clone;
    }
}
