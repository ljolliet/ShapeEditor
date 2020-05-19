/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.save.memento;

public interface Originator {
    Memento saveToMemento();
    void restoreFromMemento(Memento m);
}
