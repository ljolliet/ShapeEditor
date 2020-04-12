package editor;

import java.util.HashSet;
import java.util.Set;

public abstract class ShapeObservable implements Shape {
    private Set<ShapeObserverI> observers = new HashSet<>();

    public void addObserver(ShapeObserverI so) {
        this.observers.add(so);
    }

    public void removeObserver(ShapeObserverI so) {
        this.observers.remove(so);
    }

    public void notifyObservers() {
        Set<ShapeObserverI> copy = new HashSet<>(this.observers);
        for (ShapeObserverI u: copy)
            u.update(this);
    }

    @Override
    public Shape clone() throws CloneNotSupportedException{
        return (Shape)super.clone();
    }

}
