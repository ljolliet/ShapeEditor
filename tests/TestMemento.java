/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
import editor.save.memento.Memento;

public class TestMemento implements Memento {
    private final String state;

    public TestMemento(String state) {
        this.state = state;
    }

    @Override
    public void restore() {
    }

    @Override
    public String getState() {
        return state;
    }
}
