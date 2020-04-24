package editor.shapes;

import editor.observer.Observable;

public abstract class Shape extends Observable implements ShapeI {

    @Override
    public ShapeI clone() throws CloneNotSupportedException {
        return (ShapeI) super.clone();
    }
}