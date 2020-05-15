package editor.observer;

public interface ObservableI {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void removeObservers();
    void notifyObservers();
}
