package editor;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    List<ShapeObservable> shapes;

    public Scene() {
        this.shapes = new ArrayList<>();
    }

    public void addShape(ShapeObservable shape) {
        shapes.add(shape);
    }

    public void removeShape(ShapeObservable shape) {
        shapes.remove(shape);
    }

    public List<Shape> getShapes() {
        // TODO clones shapes?
        return new ArrayList<>(shapes);
    }
}
