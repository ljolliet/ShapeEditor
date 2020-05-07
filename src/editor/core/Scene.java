package editor.core;

import editor.shapes.ShapeI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scene implements Serializable {

    List<ShapeI> shapes;
    List<ShapeI> selectedShapes;

    public Scene() {
        this.shapes = new ArrayList<>();
        this.selectedShapes = new ArrayList<>();
    }

    public void addShape(ShapeI shape) {
        shapes.add(shape);
    }

    public void removeShape(ShapeI shape) {
        shapes.remove(shape);
    }

    public void setSelectedShapes(List<ShapeI> shapes){
        this.selectedShapes = shapes;
    }
    public List<ShapeI> getShapes() {
        return new ArrayList<>(shapes);
    }

    public List<ShapeI> getSelectedShapes() {
        return new ArrayList<>(selectedShapes);
    }

    public void setShapes(List<ShapeI> shapes) {
        this.shapes = shapes;
    }

    public boolean contains(ShapeI shape) {
        return this.shapes.contains(shape);
    }

    public void accept(EditorVisitor v) {
        v.visit(this);
    }
}
