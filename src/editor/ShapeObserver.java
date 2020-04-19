package editor;

import ui.Rendering;

import java.io.Serializable;

public class ShapeObserver implements ShapeObserverI, Serializable {    //TODO remove serializable

    Rendering rendering;
    Editor editor;  //TODO Editor Singleton ?

    public ShapeObserver(Rendering rendering, Editor e) {
        this.rendering = rendering;
        this.editor = e;
    }

    @Override
    public void update() {
        this.rendering.drawEditor();
        this.editor.saveToMemento();
    }
}
