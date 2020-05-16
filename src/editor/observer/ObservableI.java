package editor.observer;

import java.util.Set;

public interface ObservableI {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void removeObservers();
    void notifyObservers();
    Set<Observer> getObservers();
}
