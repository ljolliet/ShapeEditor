package editor.save.memento;

public interface Memento {
    void restore();
    String getState();
}
