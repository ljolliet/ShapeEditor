package editor.save;

public interface Memento {
    void restore();
    String getState();
}
