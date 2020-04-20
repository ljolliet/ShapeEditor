package editor;

import java.io.Serializable;

public class ShapeObserver implements Observer, Serializable {

    @Override
    public void update() {
        Editor.getInstance().getRendering().drawEditor();
        Editor.getInstance().saveToMemento();
        System.out.println("Observer : update");
    }
}
