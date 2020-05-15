package editor.observer;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable implements ObservableI {

    private Set<Observer> observers = new HashSet<>();

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

    @Override
    public void removeObservers() {
        this.observers = new HashSet<>();
    }

    public Set<Observer> getObservers(){
        return this.observers;
    }
}

