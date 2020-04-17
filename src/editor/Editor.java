package editor;

import editor.utils.Point2D;
import editor.utils.SelectionRectangle;
import editor.utils.SelectionShape;
import ui.Rendering;

public class Editor {
    private final Scene scene;

    private final Toolbar toolbar;
    private Rendering rendering;
    private ShapeObserverI observer;

    private Shape shapeDragged;
    private final SelectionShape selectionShape = new SelectionRectangle();

    public Editor() {
        this.scene = new Scene();
        this.toolbar = new Toolbar();
    }

    public void setRendering(Rendering r) {
        this.rendering = r;
        this.observer = new ShapeObserver(rendering);
    }

    public void addShapeToScene(ShapeObservable shape) {
        this.scene.addShape(shape);
        shape.addObserver(this.observer);

        if (this.rendering != null)
            this.rendering.drawEditor();
    }

    public void removeShapeFromScene(ShapeObservable s) {
        this.scene.removeShape(s);

        if (this.rendering != null)
            this.rendering.drawEditor();
    }

    public void addShapeToToolbar(Shape shape) {
        this.toolbar.addShape(shape);

        if (this.rendering != null)
            this.rendering.drawEditor();
    }

    public void removeShapeFromToolbar(Shape s) {
        this.toolbar.removeShape(s);

        if (this.rendering != null)
            this.rendering.drawEditor();
    }

    public Scene getScene() {
        return scene;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public Shape getShapeDragged() {
        return shapeDragged;
    }

    public void setShapeDragged(Shape shapeDragged) {
        this.shapeDragged = shapeDragged;
    }

    public SelectionShape getSelectionShape() {
        return this.selectionShape;
    }

}
