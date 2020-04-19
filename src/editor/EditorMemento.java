package editor;

public class EditorMemento implements Memento {
    private final String backup;
    private final Editor editor;

    public EditorMemento(Editor editor) {
        this.editor = editor;
        this.backup = editor.backup();
    }

    @Override
    public void restore() {
        this.editor.restore(backup);
    }
}
