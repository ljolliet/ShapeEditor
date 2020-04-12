package java.editor;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    List<Shape> shapes;

    public Scene() {
        this.shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public List<Shape> getShapes() {
        // TODO clones shapes?
        return shapes;
    }
}
