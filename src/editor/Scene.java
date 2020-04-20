package editor;

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

    public void setSelectedShapes(ArrayList<Shape> shapes){
        this.selectedShapes = shapes;
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
}
