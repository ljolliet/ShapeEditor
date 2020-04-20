package editor;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable{
    private Set<Observer> observers = new HashSet<>();

    public void addObserver(Observer so) {
        this.observers.add(so);
    }

    public void removeObserver(Observer so) {
        this.observers.remove(so);
    }

    public void notifyObservers() {
        Set<Observer> copy = new HashSet<>(this.observers);
        for (Observer u: copy)
            u.update();
    }
}
