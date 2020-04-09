package editor;

import editor.utils.Color;
import editor.utils.Vec2D;
import ui.ApplicationI;

import java.util.ArrayList;
import java.util.List;

public class Toolbar {

    List<Shape> shapes;

    public Toolbar() {
        this.shapes = new ArrayList<>();

        addShape(new Rectangle(200, 100, 0,  new Vec2D(0,0), new Color(55,55,255), new Vec2D(0,0), 0));
        addShape(new Polygon(6, 100,  new Vec2D(0,0), new Color(255,55,55), new Vec2D(0,0), Math.PI));
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
