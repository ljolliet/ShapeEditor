package editor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scene implements Serializable {

    List<ShapeObservable> shapes;
    List<ShapeObservable> selectedShapes;

    public Scene() {
        this.shapes = new ArrayList<>();
        this.selectedShapes = new ArrayList<>();
    }

    public void addShape(ShapeObservable shape) {
        shapes.add(shape);
    }

    public void removeShape(ShapeObservable shape) {
        shapes.remove(shape);
    }

    public void setSelectedShapes(ArrayList<ShapeObservable> shapes){
        this.selectedShapes = shapes;
    }
    public List<ShapeObservable> getShapes() {
        return new ArrayList<>(shapes);
    }

    public List<ShapeObservable> getSelectedShapes() {
        return new ArrayList<>(selectedShapes);
    }

    public void setShapes(List<ShapeObservable> shapes) {
        this.shapes = shapes;
    }
}
