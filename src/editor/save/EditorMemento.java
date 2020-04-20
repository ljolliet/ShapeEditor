package editor.save;

import editor.Editor;

public class EditorMemento implements Memento {
    private final String state;

    public EditorMemento(String state) {
        this.state = state;
    }

    @Override
    public void restore() {
        Editor.getInstance().restoreFromMemento(this);
    }

    public String getState() {
        return state;
    }
}
