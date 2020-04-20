package editor.save;

public interface Originator {
    Memento saveToMemento();
    void restoreFromMemento(Memento m);
}
