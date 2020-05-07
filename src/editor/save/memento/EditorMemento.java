package editor.save.memento;

import editor.core.Editor;

public class EditorMemento implements Memento {
    private final String state;

    public EditorMemento(String state) {
        this.state = state;
    }

    @Override
    public void restore() {
        Editor.getInstance().restoreFromMemento(this);
    }

    @Override
    public String getState() {
        return state;
    }
}
