package editor;

import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.SelectionShape;
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

    /**
     * @return true if the position is contained by a child shape
     */
    @Override
    public boolean contains(Point2D position) {
        for(Shape child : shapes)
            if(child.contains(position))
                return true;
        return false;
    }

    @Override
    public boolean contained(SelectionShape s) {
        return false;
    }

    /*
     * TODO Write all setters & getters
     */

    @Override
    public void setPosition(Point2D pos) {
        //TODO set position of a group
    }

    @Override
    public void setColor(Color color) {
        for (Shape s: shapes)
            s.setColor(color);
    }

    @Override
    public void setRotation(double angle) {

    }

    @Override
    public void setRotationCenter(Point2D pos) {

    }

    @Override
    public void setTranslation(Vec2D translation) {
        for (Shape s: shapes)
            s.setTranslation(translation);
    }

    @Override
    public Point2D getPosition() {
        return null;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public double getRotation() {
        return 0;
    }

    @Override
    public Point2D getRotationCenter() {
        return null;
    }

    @Override
    public Vec2D getTranslation() {
        return null;
    }
}
