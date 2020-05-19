/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.observer;

import editor.core.Editor;

import java.io.Serializable;

public class ShapeObserver implements Observer, Serializable {

    @Override
    public void update() {
        Editor.getInstance().getRendering().drawEditor();
        Editor.getInstance().saveToMemento();
        System.out.println("Observer : update");
    }
}
