package editor;

public class EditorMemento implements Memento {
    private final String state;
    private final Editor editor;

    public EditorMemento(Editor editor, String state) {
        this.editor = editor;
        this.state = state;
    }

    @Override
    public void restore() {
        this.editor.restoreFromMemento(this);
    }

    @Override
    public String getState() {
        return state;
    }
}
