package editor;

public interface Memento {
    void restore();
    String getState();
}
