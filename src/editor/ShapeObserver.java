package editor;

import ui.Rendering;

public class ShapeObserver implements ShapeObserverI {

    Rendering rendering;

    public ShapeObserver(Rendering rendering) {
        this.rendering = rendering;
    }

    @Override
    public void update(Shape s) {
        //redraw rendering
        this.rendering.drawEditor();
    }
}
