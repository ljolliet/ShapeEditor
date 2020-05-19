/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
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
