package editor;

import editor.utils.SelectionRectangle;
import editor.utils.SelectionShape;
import ui.Rendering;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Base64;

public class Editor implements Originator, Serializable{
    private Scene scene;
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
    public Memento saveToMemento(){
        String state = "";
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream( baos );
//            List<ShapeObservable> shapes = this.scene.getShapes();
//            oos.writeObject(shapes);
//            oos.close();
//            state =  Base64.getEncoder().encodeToString(baos.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(state);
        Memento m = new EditorMemento(this, state);
        history.push(m);
        return m;
    }

    @Override
    public void restoreFromMemento(Memento m) {
        try {
            byte[] data = Base64.getDecoder().decode(m.getState());
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            this.scene = (Scene) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            System.out.print("ClassNotFoundException occurred.");
        } catch (IOException e) {
            System.out.print("IOException occurred.");
        }
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
