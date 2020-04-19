package editor;

import ui.Rendering;

public class ShapeObserver implements ShapeObserverI {

    Rendering rendering;
    Editor editor;  //TODO Editor Singleton ?

    public ShapeObserver(Rendering rendering, Editor e) {
        this.rendering = rendering;
        this.editor = e;
    }

    @Override
    public void update() {
        this.rendering.drawEditor();
        this.editor.createMemento();
    }
}
