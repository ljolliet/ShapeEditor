package editor;

import editor.utils.Vec2D;
import ui.Rendering;

import java.util.HashSet;
import java.util.Set;

public class ShapeGroup extends ShapeObservable {

    private Set<Shape> shapes = new HashSet<>();
    private Rendering rendering;

    @Override
    public void addShape(Shape s) {
        shapes.add(s);
    }

    @Override
    public void removeShape(Shape s) {
        shapes.remove(s);
    }

    @Override
    public Set<Shape> getChild() {
        return new HashSet<>(shapes);
    }

    @Override
    public void collapse() {
        //TODO how to collapse a group ?
    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        return (Shape) super.clone();
    }

    @Override
    public void drawInScene(Rendering rendering) {
        for(Shape s : shapes)
            s.drawInScene(rendering);
    }

    @Override
    public void drawInToolbar(Rendering rendering) {
        for(Shape s : shapes)
            s.drawInToolbar(rendering);
    }

    @Override
    public boolean contains(Vec2D pos) {
        return false;   //TODO true if the group contains the position
    }

    @Override
    public void setPosition(Vec2D pos) {
        //TODO set position of a group
    }
}
