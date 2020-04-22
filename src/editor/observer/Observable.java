package editor.observer;

import java.util.HashSet;
import java.util.Set;

public class Observable implements ObservableI{
    private final Set<Observer> observers = new HashSet<>();

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        Set<Observer> copy = new HashSet<>(this.observers);
        for (Observer o : copy)
            o.update();
    }
}
