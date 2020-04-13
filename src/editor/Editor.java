package editor;

import ui.Rendering;

public class Editor {

    private final Scene scene;
    private final Toolbar toolbar;
    private Rendering rendering;
    private ShapeObserverI observer;

    public Editor() {
        this.scene = new Scene();
        this.toolbar = new Toolbar();
    }

    public void setRendering(Rendering r) {
        this.rendering = r;
        this.observer = new ShapeObserver(rendering);
    }

    public void addShapeInScene(ShapeObservable shape) {
        this.scene.addShape(shape);
        shape.addObserver(this.observer);

        if (this.rendering != null)
            this.rendering.drawEditor();
    }

    public void addShapeInToolbar(Shape shape) {
        this.toolbar.addShape(shape);

        if (this.rendering != null)
            this.rendering.drawEditor();
    }

    public Scene getScene() {
        return scene;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
