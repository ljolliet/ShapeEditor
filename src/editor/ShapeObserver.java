package editor;

import java.io.Serializable;

public class ShapeObserver implements ShapeObserverI, Serializable {

    @Override
    public void update() {
        Editor.getInstance().getRendering().drawEditor();
        Editor.getInstance().saveToMemento();
    }
}
