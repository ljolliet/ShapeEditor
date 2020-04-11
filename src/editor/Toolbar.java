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

        addShape(new Rectangle(100, 50, 20,  new Vec2D(0,0), new Color(55,55,255), new Vec2D(50,25), 0));
        addShape(new Rectangle(100, 50, 0,  new Vec2D(0,0), new Color(55,255,255), new Vec2D(50,25), 0));
        addShape(new Polygon(6, 50,  new Vec2D(0,0), new Color(255,55,55), new Vec2D(0,0), 90));
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
