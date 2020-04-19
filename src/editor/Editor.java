package editor;

import editor.utils.SelectionRectangle;
import editor.utils.SelectionShape;
import ui.Rendering;

public class Editor implements Originator{
    private final Scene scene;
    private final Toolbar toolbar;
    private Rendering rendering;
    private ShapeObserverI observer;
    private Caretaker history;

    private Shape shapeDragged;
    private final SelectionShape selectionShape = new SelectionRectangle();

    public Editor() {
        this.scene = new Scene();
        this.toolbar = new Toolbar();
        this.history = new Caretaker();
    }

    public void setRendering(Rendering r) {
        this.rendering = r;
        this.observer = new ShapeObserver(rendering, this);
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

    @Override
    public Memento createMemento() {
        Memento m = new EditorMemento(this);
        history.push(m);
        return m;
    }

    @Override
    public String backup() {
        return "backup : " + this.scene.getShapes().size() + " shapes"; //TODO : real backup
    }

    @Override
    public void restore(String m) {
        System.out.println(backup());
    }

    public void undo() {
        if(this.history.undo())
            this.rendering.drawEditor();
    }

    public void redo() {
        if(this.history.redo())
            this.rendering.drawEditor();
    }
}
