package editor.core;

import editor.observer.Observable;
import editor.observer.Observer;
import editor.observer.ShapeObserver;
import editor.save.io.ExportManager;
import editor.save.io.ImportManager;
import editor.save.io.json.JSONExportVisitor;
import editor.save.io.json.JSONImportManager;
import editor.save.memento.Caretaker;
import editor.save.memento.EditorMemento;
import editor.save.memento.Memento;
import editor.save.memento.Originator;
import editor.shapes.*;
import editor.utils.SelectionRectangle;
import editor.utils.SelectionShape;
import ui.Rendering;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class Editor extends Observable implements Originator {
    private static final int SIZE_HISTORY = 50;
    private static Editor instance;
    private final Scene scene;
    private final Toolbar toolbar;
    private final Caretaker history;
    private final SelectionShape selectionShape;
    private Rendering rendering;
    private Observer observer;
    private ShapeI shapeDragged;
    private ExportManager exportVisitor;
    private ImportManager importManager;

    private Editor() {
        this.scene = new Scene();
        this.toolbar = new Toolbar();
        this.history = new Caretaker(SIZE_HISTORY);
        this.selectionShape = new SelectionRectangle();
        this.observer = new ShapeObserver();
        this.exportVisitor = new JSONExportVisitor();
        this.importManager = new JSONImportManager();
        this.addObserver(observer);
        this.saveToMemento();
    }

    public static Editor getInstance() {
        return instance != null ? instance : (instance = new Editor());
    }


    public void setRendering(Rendering r) {
        this.rendering = r;
    }

    public void addShapeToScene(Shape shape) {
        this.scene.addShape(shape);
        shape.addObserver(this.observer);
        notifyObservers();
    }

    public void removeShapeFromScene(Shape s) {
        this.scene.removeShape(s);
        notifyObservers();
    }

    public void addShapeToToolbar(ShapeI shape) {
        this.toolbar.addShape(shape);
        notifyObservers();
    }

    public void removeShapeFromToolbar(ShapeI s) {
        this.toolbar.removeShape(s);
        notifyObservers();
    }

    public void deGroup(Shape group) {
        scene.removeShape(group);
        scene.clearSelectedShapes();
        for(ShapeI child : group.getChildren()){
            scene.addShape((Shape) child);
            ((Shape) child).addObserver(observer);
        }
        notifyObservers();
    }

    public void createGroup(List<ShapeI> shapes) {
        scene.clearSelectedShapes();
        ShapeGroup group = new ShapeGroup();
        for(ShapeI s : shapes){
            scene.removeShape((Shape) s);
            ((Shape) s).removeObservers();
            group.addShape(s);
        }
        group.addObserver(Editor.getInstance().getObserver());
        scene.addShape(group);
        notifyObservers();
    }

    @Override
    public Memento saveToMemento(){
        String state = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject(new List[]{this.scene.getShapes(), this.toolbar.getShapes()});
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
            List list[] = (List[]) ois.readObject();
            this.scene.setShapes(list[0]);
            for(Shape s : this.scene.getShapes())
                s.addObserver(this.observer);
            this.toolbar.setShapes(list[1]);
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
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

    public void saveToolbar() {
        this.exportVisitor.saveToolbar();
    }

    public void restoreToolbar() {
        this.importManager.restoreToolbar();
        this.toolbar.checkInitialised();
    }


    public void saveScene(File file) {
        this.exportVisitor.saveScene(file);
    }

    public void restoreScene(File file) {
        this.importManager.restore(file);
        history.clear();
        this.saveToMemento();
        if(rendering != null)
            rendering.drawEditor();
    }

    public void accept(EditorVisitor visitor) {
        visitor.visit(this);
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

    public ShapeI getShapeDragged() {
        return shapeDragged;
    }

    public ExportManager getExportVisitor() {
        return exportVisitor;
    }

    public ImportManager getImportManager() {
        return importManager;
    }

    public void setShapeDragged(ShapeI shapeDragged) {
        this.shapeDragged = shapeDragged;
    }

    public boolean toolbarContains(ShapeI shape) {
        return this.toolbar.contains(shape);
    }

    public boolean sceneContains(Shape  shape) {
        return this.scene.contains(shape);
    }

    public String getSaveExtension() {
        return this.exportVisitor.getExtension();
    }

    public Observer getObserver() {
        return this.observer;
    }

    public void setSceneSelectedShapes(List<ShapeI> shapes) {
        scene.setSelectedShapes(shapes);
    }

}
