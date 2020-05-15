package editor.core;

import editor.shapes.Shape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scene implements Serializable {

    List<Shape> shapes;
    List<Shape> selectedShapes;

    public Scene() {
        this.shapes = new ArrayList<>();
        this.selectedShapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public void setSelectedShapes(List<Shape> shapes){
        this.selectedShapes = shapes;
    }
    public void clearSelectedShapes() {
        this.selectedShapes.clear();
    }

    public List<Shape> getShapes() {
        return new ArrayList<>(shapes);
    }

    public List<Shape> getSelectedShapes() {
        return new ArrayList<>(selectedShapes);
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public boolean contains(Shape shape) {
        return this.shapes.contains(shape);
    }

    public void accept(EditorVisitor v) {
        v.visit(this);
    }


}
