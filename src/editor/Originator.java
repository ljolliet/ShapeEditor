package editor;

public interface Originator {
    Memento createMemento();
    String backup();
    void restore(String m);
}
