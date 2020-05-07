package editor.save.memento;

public interface Originator {
    Memento saveToMemento();
    void restoreFromMemento(Memento m);
}
