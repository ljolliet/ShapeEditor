package editor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class ShapeObservable implements Shape {
    private List<ShapeObserverI> observersOrdered = new LinkedList<ShapeObserverI>();
    private Set<ShapeObserverI> observersSet = new HashSet<ShapeObserverI>();

    @Override
    public void addObserver(ShapeObserverI so) {
        if (!observersSet.contains(so)) {
            observersOrdered.add(so);
            observersSet.add(so);
        }
    }

    @Override
    public void removeObserver(ShapeObserverI so) {
        observersOrdered.remove(so);
        observersSet.remove(so);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void notifyObservers() {
        Object[] copy = observersOrdered.toArray();
        for (Object u : copy)
            ((ShapeObserverI) u).update(this);
    }
}
