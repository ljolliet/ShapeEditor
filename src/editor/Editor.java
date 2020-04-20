package editor;

import editor.save.Caretaker;
import editor.save.EditorMemento;
import editor.save.Memento;
import editor.save.Originator;
import editor.utils.SelectionRectangle;
import editor.utils.SelectionShape;
import ui.Rendering;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class Editor implements Originator {
    private static final int SIZE_HISTORY = 50;
    private static Editor instance;
    private final Scene scene;
    private final Toolbar toolbar;
    private final Caretaker history;
    private final SelectionShape selectionShape;
    private Rendering rendering;
    private ShapeObserverI observer;
    private Shape shapeDragged;

    private Editor() {
        this.scene = new Scene();
        this.toolbar = new Toolbar();
        this.history = new Caretaker(SIZE_HISTORY);
        this.selectionShape = new SelectionRectangle();
        this.observer = new ShapeObserver();
    }

    public static Editor getInstance() {
        return instance != null ? instance : (instance = new Editor());
    }


    public void setRendering(Rendering r) {
        this.rendering = r;
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

    @Override
    public Memento saveToMemento(){
        String state = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject(this.scene.getShapes());
            oos.close();
            state =  Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Memento m = new EditorMemento(state);
        history.push(m);
        return m;
    }

    @Override
    public void restoreFromMemento(Memento m) {
        try {
            EditorMemento em = (EditorMemento)m;
            byte[] data = Base64.getDecoder().decode(em.getState());
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            this.scene.setShapes((List<ShapeObservable>) ois.readObject());
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

    public Scene getScene() {
        return scene;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public Caretaker getHistory() {
        return this.history;
    }

    public SelectionShape getSelectionShape() {
        return this.selectionShape;
    }

    public Rendering getRendering() {
        return this.rendering;
    }

    public Shape getShapeDragged() {
        return shapeDragged;
    }

    public void setShapeDragged(Shape shapeDragged) {
        this.shapeDragged = shapeDragged;
    }
}
